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
package org.jboss.hal.config;

import com.google.gwt.core.client.GWT;

/**
 * @author Harald Pehl
 */
public abstract class AbstractEndpoints implements Endpoints {

    @Override
    public String dmr() {
        return GWT.isScript() ? getBaseUrl() + "management" : "http://" + getDevHost() + ":" + getDevPort() + "/app/proxy";
    }

    @Override
    public String deployment() {
        return GWT.isScript() ? getBaseUrl() + "management/add-content" : "http://" + getDevHost() + ":" + getDevPort() + "/app/upload";
    }

    @Override
    public String patch() {
        return GWT.isScript() ? getBaseUrl() + "management-upload" : "http://" + getDevHost() + ":" + getDevPort() + "/app/patch";
    }

    @Override
    public String logout() {
        return GWT.isScript() ? getBaseUrl() + "logout" : "http://" + getDevHost() + ":" + getDevPort() + "/app/logout";
    }

    protected abstract String getDevHost();

    protected abstract String getDevPort();

    private String getBaseUrl() {
        String base = GWT.getHostPageBaseURL();
        return extractHttpEndpointUrl(base);
    }

    private String extractHttpEndpointUrl(String base) {
        String protocol = base.substring(0, base.indexOf("//") + 2);
        String remainder = base.substring(base.indexOf(protocol) + protocol.length(), base.length());

        String host;
        String port;

        int portDelim = remainder.indexOf(":");
        if (portDelim != -1) {
            host = remainder.substring(0, portDelim);
            String portRemainder = remainder.substring(portDelim + 1, remainder.length());
            if (portRemainder.contains("/")) {
                port = portRemainder.substring(0, portRemainder.indexOf("/"));
            } else {
                port = portRemainder;
            }
        } else {
            host = remainder.substring(0, remainder.indexOf("/"));
            if ("https://".equalsIgnoreCase(protocol)) {
                port = "443";
            } else {
                port = "80";
            }
        }

        // default url
        return protocol + host + ":" + port + "/";
    }
}
