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
package org.jboss.hal.client;

import static org.jboss.hal.config.ProductInfo.Variant.COMMUNITY;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.hal.client.bootstrap.BootstrapContext;
import org.jboss.hal.client.bootstrap.BootstrapProcess;
import org.jboss.hal.client.resources.Resources;
import org.jboss.hal.config.ProductInfo;
import org.uberfire.client.UberFirePreferences;
import org.uberfire.client.callbacks.Callback;
import org.uberfire.client.workbench.Workbench;
import org.uberfire.client.workbench.events.ApplicationReadyEvent;

/**
 * HAL's entry point
 */
@EntryPoint
@SuppressWarnings("UnusedDeclaration")
public class HAL {

    @Inject Resources resources;
    @Inject ProductInfo productInfo;
    @Inject LoadingPanel loadingPanel;
    @Inject BootstrapProcess bootstrapProcess;
    @Inject Workbench workbench;

    /**
     * Gets invoked early in the startup sequence, as soon as all this bean's
     * {@code @Inject}'ed fields are initialized. Errai Bus and UberFire services
     * are not yet available.
     */
    @PostConstruct
    private void earlyInit() {
        Log.info("Start HAL initialization");

        // UberFire setup
        UberFirePreferences.setProperty("org.uberfire.client.workbench.widgets.listbar.context.disable", true);

        // Make sure JavaScript & CSS resources are in place
        injectResources();

        // Display loading panel
        RootPanel.get().add(loadingPanel);

        // Kick off the bootstrap process
        workbench.addStartupBlocker(BootstrapProcess.class);
        bootstrapProcess.go(loadingPanel.getProgress(), new Callback<BootstrapContext>() {
            @Override
            public void callback(final BootstrapContext context) {
                if (context.hasError()) {
                    initFailed(context);
                } else {
                    // Will delegate to finalInit()
                    workbench.removeStartupBlocker(BootstrapProcess.class);
                }
            }
        });
    }

    private void injectResources() {
        if (productInfo.getHalVariant() == COMMUNITY) {
            resources.community().ensureInjected();
        } else {
            resources.product().ensureInjected();
        }
        resources.prettifyCss().ensureInjected();
        resources.progressPolyfillCss().ensureInjected();
        ScriptInjector.fromString(resources.prettifyJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(resources.lunrJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(resources.mousetrapJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
        ScriptInjector.fromString(resources.progressPolyfillJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
    }

    private void initFailed(final BootstrapContext context) {
        Log.error("HAL failed to start: " + context.getErrorMessage());
        loadingPanel.setVisible(false);
        // TODO Show error popup
    }

    /**
     * Gets invoked late in the startup sequence, when all UberFire framework
     * bootstrapping has completed.
     */
    private void finalInit(@Observes final ApplicationReadyEvent event) {
        Log.info("Finish HAL initialization");
        loadingPanel.setVisible(false);
    }
}
