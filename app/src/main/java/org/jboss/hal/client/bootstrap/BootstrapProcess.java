/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.hal.client.bootstrap;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.gwt.flow.Async;
import org.jboss.gwt.flow.Function;
import org.jboss.gwt.flow.Outcome;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.client.bootstrap.steps.ExecutionMode;
import org.jboss.hal.client.bootstrap.steps.LoadGoogleViz;
import org.uberfire.client.callbacks.Callback;

/**
 * @author Harald Pehl
 */
@ApplicationScoped
public class BootstrapProcess {

    private final BootstrapContext context;
    private final List<Function<BootstrapContext>> steps;

    @Inject
    public BootstrapProcess(BootstrapContext context, LoadGoogleViz loadGoogleViz, ExecutionMode executionMode) {
        this.context = context;
        this.steps = new ArrayList<>(asList(loadGoogleViz, executionMode));
    }

    @SuppressWarnings("unchecked")
    public void go(final Progress progress, final Callback<BootstrapContext> callback) {

        // All information is contained in the context, thus we can safely delegate to the callback.
        Outcome<BootstrapContext> outcome = new Outcome<BootstrapContext>() {
            @Override
            public void onFailure(final BootstrapContext context) {
                callback.callback(context);
            }

            @Override
            public void onSuccess(final BootstrapContext context) {
                callback.callback(context);
            }
        };

        new Async<BootstrapContext>(progress).waterfall(context, outcome, steps.toArray(new Function[steps.size()]));
    }
}
