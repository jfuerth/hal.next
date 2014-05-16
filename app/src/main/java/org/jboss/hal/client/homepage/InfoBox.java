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
@Templated("Homepage.html#infoBoxRoot")
public class InfoBox extends Composite {

    static final String ID_PREFIX = "info-box-";
    @DataField("infoBoxHeader") HeadingElement header = Document.get().createHElement(3);
    @Inject @DataField("infoBoxLink") InlineHyperlink link;
    @Inject @DataField("infoBoxBody") Label body;
    @Inject LessStyle less;

    public InfoBox using(final String token, final String title, final String description) {
        link.setTargetHistoryToken(token);
        link.setText(title);
        link.ensureDebugId(ID_PREFIX + "link-" + token);
        body.setText(description);
        ensureDebugId(ID_PREFIX + token);
        return this;
    }
}
