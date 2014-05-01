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

package org.jboss.hal.dmr.client.dispatch.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.hal.dmr.client.dispatch.Action;
import org.jboss.hal.dmr.client.dispatch.ActionHandler;
import org.jboss.hal.dmr.client.dispatch.ActionType;
import org.jboss.hal.dmr.client.dispatch.HandlerMapping;

/**
 * @author Heiko Braun
 * @date 3/17/11
 */
@ApplicationScoped
public class HandlerRegistry implements HandlerMapping {

    private Map<ActionType, ActionHandler> registry = new HashMap<ActionType, ActionHandler>();

    @Inject
    public HandlerRegistry(DMRHandler dmrhandler) {
        register(ActionType.DMR, dmrhandler);
    }

    @Override
    public ActionHandler resolve(Action action) {
        return registry.get(action.getType());
    }

    @Override
    public void register(ActionType actionType, ActionHandler handler)
    {
        registry.put(actionType, handler);
    }
}
