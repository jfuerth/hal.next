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
package org.jboss.hal.config.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import org.jboss.errai.codegen.builder.impl.ClassBuilder;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.config.rebind.AbstractAsyncGenerator;
import org.jboss.errai.config.rebind.GenerateAsync;
import org.jboss.hal.config.client.AbstractEndpoints;
import org.jboss.hal.config.client.Endpoints;

@GenerateAsync(Endpoints.class)
public class EndpointsGenerator extends AbstractAsyncGenerator {

    private static final String PACKAGE_NAME = Endpoints.class.getPackage().getName();
    private static final String CLASS_NAME = Endpoints.class.getSimpleName() + "Impl";

    @Override
    public String generate(final TreeLogger logger, final GeneratorContext context, final String typeName)
            throws UnableToCompleteException {
        return startAsyncGeneratorsAndWaitFor(Endpoints.class, context, logger, PACKAGE_NAME, CLASS_NAME);
    }

    @Override
    protected String generate(final TreeLogger logger, final GeneratorContext context) {

        String devHost = GeneratorUtils.failSafeGetProperty(context.getPropertyOracle(), "dev.host", "127.0.0.1");
        String devPort = GeneratorUtils.failSafeGetProperty(context.getPropertyOracle(), "dev.port", "8888");

        return ClassBuilder.define(PACKAGE_NAME + "." + CLASS_NAME, AbstractEndpoints.class)
                .publicScope().implementsInterface(Endpoints.class)
                .body()
                .protectedMethod(String.class, "getDevHost")
                .body().append(Stmt.loadLiteral(devHost).returnValue()).finish()
                .protectedMethod(String.class, "getDevPort")
                .body().append(Stmt.loadLiteral(devPort).returnValue()).finish()
                .toJavaString();
    }
}
