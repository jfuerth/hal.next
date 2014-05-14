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
package org.jboss.hal.client.bootstrap;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.hal.config.ProductInfo;
import org.jboss.hal.model.rbac.StandardRole;

/**
 * @author Heiko Braun
 * @date 2/11/11
 */
@ApplicationScoped
public class BootstrapContext {

    private final ProductInfo productInfo;
    private boolean standalone;
    private Throwable error;
    private String principal;
    private Set<String> roles;
    private String runAs;

    @Inject
    public BootstrapContext(final ProductInfo productInfo) {this.productInfo = productInfo;}

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    public void setError(final Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }

    public String getErrorMessage() {
        //noinspection ThrowableResultOfMethodCallIgnored
        return hasError() ? null : getError().getMessage();
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public boolean isSuperUser() {
        boolean match = false;
        for(String role : roles)
        {
            if(StandardRole.SUPER_USER.equalsIgnoreCase(role))
            {
                match = true;
                break;
            }
        }
        return match;
    }

    public boolean isAdmin() {
        boolean match = false;
        for(String role : roles)
        {
            if(StandardRole.ADMINISTRATOR.equalsIgnoreCase(role))
            {
                match = true;
                break;
            }
        }
        return match;
    }

    public void setRunAs(final String runAs) {
        this.runAs = runAs;
    }

    public String getRunAs() {
        return runAs;
    }
}
