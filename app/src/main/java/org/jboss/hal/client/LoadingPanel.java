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
import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.local.spi.LessStyle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jboss.gwt.flow.Progress;
import org.jboss.hal.client.widgets.progress.ProgressElement;

/**
 * @author Harald Pehl
 */
@Templated("#loadingPanel")
public class LoadingPanel extends Composite {

    @Inject LessStyle less;
    @Inject @DataField Label message;
    @DataField ProgressElement progress = new ProgressElement();
    @DataField DivElement container = Document.get().createDivElement();

    @PostConstruct
    public void style() {
        message.setStyleName(less.get("loadingPanel-message"));
        container.setClassName(less.get("loadingPanel-container"));
        getElement().setClassName(less.get("loadingPanel-root"));
    }

    public Progress getProgress() {
        return progress;
    }
}
