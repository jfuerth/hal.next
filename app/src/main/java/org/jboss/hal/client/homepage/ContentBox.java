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
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;

/**
 * A content box on the homepage.
 *
 * @author Harald Pehl
 */
@Dependent
public class ContentBox extends Composite implements OpenHandler<DisclosurePanel>, CloseHandler<DisclosurePanel> {

    static final String ID_PREFIX = "content-box-";

    DisclosurePanel dp;
    @Inject ContentBoxHeader header;
    @Inject ContentBoxBody body;

    @PostConstruct
    public void init() {
        dp = new DisclosurePanel();
        dp.setHeader(header);
        dp.addOpenHandler(this);
        dp.addCloseHandler(this);
        dp.setOpen(true);
        dp.add(body);
        initWidget(dp);
        setStyleName("homepage-content-box");
    }

    public ContentBox using(final String id, final String header, final SafeHtml body, final String linkTitle,
            final String linkTarget) {
        this.header.header.setText(header);
        this.header.header.ensureDebugId(ID_PREFIX + "header-" + id);
        this.body.body.setHTML(body);
        this.body.link.setText(linkTitle);
        this.body.link.setTargetHistoryToken(linkTarget);
        this.body.link.ensureDebugId(ID_PREFIX + "link-" + id);
        ensureDebugId(ID_PREFIX + id);
        return this;
    }

    @Override
    public void onOpen(final OpenEvent<DisclosurePanel> event) {
        setIconClassname("icon-angle-down");
    }

    @Override
    public void onClose(final CloseEvent<DisclosurePanel> event) {
        setIconClassname("icon-angle-right");
    }

    private void setIconClassname(String styleName) {
        NodeList<Element> i = dp.getElement().getElementsByTagName("i");
        if (i.getLength() == 1) {
            Element iconElem = i.getItem(0);
            iconElem.setClassName(styleName);
        }
    }
}
