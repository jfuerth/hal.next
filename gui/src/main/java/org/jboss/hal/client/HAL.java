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

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.uberfire.client.UberFirePreferences;
import org.uberfire.client.workbench.events.ApplicationReadyEvent;

/**
 * HAL's entry point
 */
@EntryPoint
@SuppressWarnings("UnusedDeclaration")
public class HAL {

    /**
     * Gets invoked early in the startup sequence, as soon as all this bean's
     * {@code @Inject}'ed fields are initialized. Errai Bus and UberFire services
     * are not yet available.
     */
    @PostConstruct
    private void earlyInit() {
        // TODO Migrate the bootstrap steps
        UberFirePreferences.setProperty("org.uberfire.client.workbench.widgets.listbar.context.disable", true);
    }

    /**
     * Gets invoked late in the startup sequence, when all UberFire framework
     * bootstrapping has completed.
     */
    private void finalInit(@Observes final ApplicationReadyEvent event) {
        Log.info("HAL up and ready");
        hideLoadingPopup();
    }

    @SuppressWarnings("GwtToHtmlReferences")
    private void hideLoadingPopup() {
        RootPanel.get("loading").setVisible(false);
    }
}
