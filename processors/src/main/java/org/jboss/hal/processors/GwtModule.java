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
package org.jboss.hal.processors;

/**
 * @author Harald Pehl
 */
public enum GwtModule {
    BASE("Base.gwt.xml", "Base.gwt.xml.ftl"),
    EAP("EAP.gwt.xml", "EAP.gwt.xml.ftl"),
    EAP_DEV("EAPDev.gwt.xml", "EAPDev.gwt.xml.ftl"),
    WILDFLY_DEV("WildFlyDev.gwt.xml", "WildFlyDev.gwt.xml.ftl"),
    WILDFLY_SLIM("WildFly.gwt.xml", "WildFly.gwt.xml.ftl"),
    WILDFLY_FULL("WildFlyFull.gwt.xml", "WildFlyFull.gwt.xml.ftl");

    private final String module;
    private final String template;

    GwtModule(final String module, final String template) {
        this.module = module;
        this.template = template;
    }

    public String module() {
        return module;
    }

    public String template() {
        return template;
    }
}
