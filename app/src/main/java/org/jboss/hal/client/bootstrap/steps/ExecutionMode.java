/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.hal.client.bootstrap.steps;

import static org.jboss.hal.dmr.ModelDescriptionConstants.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jboss.gwt.flow.Control;
import org.jboss.gwt.flow.Function;
import org.jboss.hal.client.bootstrap.BootstrapContext;
import org.jboss.hal.client.core.Preferences;
import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.dispatch.DispatchAsync;
import org.jboss.hal.dmr.dispatch.impl.DMRAction;
import org.jboss.hal.dmr.dispatch.impl.DMRResponse;

/**
 * @author Heiko Braun
 * @date 5/19/11
 */
@Dependent
public class ExecutionMode implements Function<BootstrapContext> {

    private DispatchAsync dispatcher;

    @Inject
    public ExecutionMode(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void execute(final Control<BootstrapContext> control) {

        final BootstrapContext context = control.getContext();

        final ModelNode operation = new ModelNode();
        operation.get(OP).set(COMPOSITE);
        operation.get(ADDRESS).setEmptyList();

        ModelNode step;
        List<ModelNode> steps = new ArrayList<>();

        // exec type
        step = new ModelNode();
        step.get(OP).set(READ_ATTRIBUTE_OPERATION);
        step.get(NAME).set("process-type");
        step.get(ADDRESS).setEmptyList();
        steps.add(step);

        // product name
        step = new ModelNode();
        step.get(OP).set(READ_ATTRIBUTE_OPERATION);
        step.get(NAME).set("product-name");
        step.get(ADDRESS).setEmptyList();
        steps.add(step);

        // release codename
        step = new ModelNode();
        step.get(OP).set(READ_ATTRIBUTE_OPERATION);
        step.get(NAME).set("release-codename");
        step.get(ADDRESS).setEmptyList();
        steps.add(step);

        // product version
        step = new ModelNode();
        step.get(OP).set(READ_ATTRIBUTE_OPERATION);
        step.get(NAME).set("product-version");
        step.get(ADDRESS).setEmptyList();
        steps.add(step);

        // release version
        step = new ModelNode();
        step.get(OP).set(READ_ATTRIBUTE_OPERATION);
        step.get(NAME).set("release-version");
        step.get(ADDRESS).setEmptyList();
        steps.add(step);

        // whoami
        step = new ModelNode();
        step.get(OP).set("whoami");
        step.get(ADDRESS).setEmptyList();
        step.get("verbose").set(true);
        steps.add(step);

        operation.get(STEPS).set(steps);

        dispatcher.execute(new DMRAction(operation), new AsyncCallback<DMRResponse>() {

            @Override
            public void onFailure(Throwable caught) {
                context.setError(caught);
                Log.error(caught.getMessage());
                control.abort();
            }

            @Override
            public void onSuccess(DMRResponse result) {
                ModelNode response = result.get();

                if (response.isFailure()) {
                    context.setError(new RuntimeException(response.getFailureDescription()));
                    control.abort();
                } else {
                    // capture execution mode
                    ModelNode execMode = response.get(RESULT).get("step-1");
                    boolean isServer = execMode.get(RESULT).asString().equals("Server");
                    context.setStandalone(isServer);

                    // product name, release codename, product version, release version
                    String finalProductVersion = "n/a";
                    String finalProductName = "n/a";
                    ModelNode productVersion = response.get(RESULT).get("step-4");
                    ModelNode releaseVersion = response.get(RESULT).get("step-5");
                    if (productVersion.get(RESULT).isDefined()) {
                        finalProductVersion = productVersion.get(RESULT).asString();
                    } else if (releaseVersion.get(RESULT).isDefined()) {
                        finalProductVersion = releaseVersion.get(RESULT).asString();
                    }
                    ModelNode productName = response.get(RESULT).get("step-2");
                    ModelNode releaseCodename = response.get(RESULT).get("step-3");
                    if (productName.get(RESULT).isDefined()) {
                        finalProductName = productName.get(RESULT).asString();
                    } else if (releaseCodename.get(RESULT).isDefined()) {
                        finalProductName = releaseCodename.get(RESULT).asString();
                    }
                    context.getProductInfo().update(finalProductVersion, finalProductName);

                    // user and roles
                    ModelNode whoami = response.get(RESULT).get("step-6");
                    ModelNode whoamiResult = whoami.get(RESULT);
                    String username = whoamiResult.get("identity").get("username").asString();
                    context.setPrincipal(username);
                    Set<String> mappedRoles = new HashSet<>();
                    if (whoamiResult.hasDefined("mapped-roles")) {
                        List<ModelNode> roles = whoamiResult.get("mapped-roles").asList();
                        for (ModelNode role : roles) {
                            final String roleName = role.asString();
                            mappedRoles.add(roleName);
                        }
                    }
                    context.setRoles(mappedRoles);

                    if (context.isSuperUser() && Preferences.has(Preferences.Key.RUN_AS_ROLE)) {
                        String runAsRole = Preferences.get(Preferences.Key.RUN_AS_ROLE);
                        dispatcher.setProperty("run_as", runAsRole);
                        context.setRunAs(runAsRole);
                    }
                    Preferences.clear(Preferences.Key.RUN_AS_ROLE);

                    control.proceed();
                }
            }
        });
    }
}
