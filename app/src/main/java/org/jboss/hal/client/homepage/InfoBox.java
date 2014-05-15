/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
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
package org.jboss.hal.client.homepage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.ui.client.local.spi.LessStyle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

/**
 * @author Harald Pehl
 */
@Templated("#infoBox")
public class InfoBox extends Composite {

    @DataField HeadingElement header = Document.get().createHElement(3);
    @Inject @DataField InlineHyperlink link;
    @Inject @DataField Label body;
    @Inject LessStyle less;

    @PostConstruct public void style() {
        header.addClassName(less.get("homepage-info-box-header"));
        link.addStyleName(less.get("homepage-link"));
        body.addStyleName(less.get("homepage-info-box-body"));
    }

    public void update(final String token, final String title, final String description) {
        link.setTargetHistoryToken(token);
        link.setText(title);
        body.setText(description);
        getWidget().ensureDebugId("info-box-" + token);
    }
}
