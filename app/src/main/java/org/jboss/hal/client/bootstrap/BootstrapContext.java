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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.hal.model.domain.HostList;
import org.jboss.hal.model.domain.ProfileRecord;
import org.jboss.hal.model.domain.ServerInstance;
import org.jboss.hal.model.rbac.StandardRole;

/**
 * @author Heiko Braun
 * @date 2/11/11
 */
public class BootstrapContext {

    private Map<String, String> ctx = new HashMap<>();
    private boolean standalone;
    private Throwable lastError;
    private String principal;
    private boolean hostManagementDisabled;
    private boolean groupManagementDisabled;
    private Set<String> roles;
    private HostList initialHosts;
    private Set<String> addressableHosts = Collections.emptySet();
    private Set<String> addressableGroups = Collections.emptySet();
    private String runAs;
    private List<ProfileRecord> initialProfiles;
    private ServerInstance initialServer;

    public void setProperty(String key, String value) {
        ctx.put(key, value);
    }

    public String getProperty(String key) {
        return ctx.get(key);
    }

    public boolean hasProperty(String key) {
        return getProperty(key) != null;
    }

    public void removeProperty(String key) {
        ctx.remove(key);
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    public void setLastError(Throwable caught) {
        this.lastError = caught;
    }

    public Throwable getLastError() {
        return lastError;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setHostManagementDisabled(boolean b) {
        this.hostManagementDisabled = b;
    }

    public boolean isHostManagementDisabled() {
        return hostManagementDisabled;
    }

    public void setGroupManagementDisabled(boolean b) {
        this.groupManagementDisabled = b;
    }

    public boolean isGroupManagementDisabled() {
        return groupManagementDisabled;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public boolean isSuperUser() {
        boolean match = false;
        for (String role : roles) {
            if (StandardRole.SUPER_USER.equalsIgnoreCase(role)) {
                match = true;
                break;
            }
        }
        return match;
    }

    public boolean isAdmin() {
        boolean match = false;
        for (String role : roles) {
            if (StandardRole.ADMINISTRATOR.equalsIgnoreCase(role)) {
                match = true;
                break;
            }
        }
        return match;
    }

    public void setAddressableHosts(Set<String> hosts) {
        this.addressableHosts = hosts;
    }

    public Set<String> getAddressableHosts() {
        return addressableHosts;
    }

    public void setAdressableGroups(Set<String> groups) {
        this.addressableGroups = groups;
    }

    public Set<String> getAddressableGroups() {
        return addressableGroups;
    }

    public void setRunAs(final String runAs) {
        this.runAs = runAs;
    }

    public String getRunAs() {
        return runAs;
    }

    public void setInitialProfiles(final List<ProfileRecord> initialProfiles) {
        this.initialProfiles = initialProfiles;
    }

    public List<ProfileRecord> getInitialProfiles() {
        return initialProfiles;
    }

    public HostList getInitialHosts() {
        return initialHosts;
    }

    public void setInitialHosts(final HostList initialHosts) {
        this.initialHosts = initialHosts;
    }

    public void setInitialServer(final ServerInstance initialServer) {
        this.initialServer = initialServer;
    }

    public ServerInstance getInitialServer() {
        return initialServer;
    }
}
