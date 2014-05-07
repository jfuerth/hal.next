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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jboss.hal.dmr.client.dispatch.Action;
import org.jboss.hal.dmr.client.dispatch.ActionHandler;
import org.jboss.hal.dmr.client.dispatch.DispatchAsync;
import org.jboss.hal.dmr.client.dispatch.DispatchRequest;
import org.jboss.hal.dmr.client.dispatch.HandlerMapping;
import org.jboss.hal.dmr.client.dispatch.Result;

/**
 * @author Heiko Braun
 * @date 3/17/11
 */
@ApplicationScoped
public class DispatchAsyncImpl implements DispatchAsync {

    private HandlerMapping registry;
    private Map<String, String> properties = new HashMap<String, String>();

    @Inject
    public DispatchAsyncImpl(HandlerMapping registry) {
        this.registry = registry;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Action<R>, R extends Result> DispatchRequest execute(A action, AsyncCallback<R> callback) {

        ActionHandler<A, R> handler = registry.resolve(action);

        if (null == handler) {
            callback.onFailure(new IllegalStateException("No handler for type " + action.getType()));
            return null;
        }
        return handler.execute(action, callback, Collections.unmodifiableMap(properties));
    }

    @Override
    public <A extends Action<R>, R extends Result> DispatchRequest undo(A action, R result,
            AsyncCallback<Void> callback) {
        return null;
    }

    @Override
    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public void clearProperty(String key) {
        properties.remove(key);
    }
}
