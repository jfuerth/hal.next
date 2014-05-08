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

import static org.jboss.hal.config.rebind.GeneratorUtils.failSafeGetProperty;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import org.jboss.errai.codegen.builder.impl.ClassBuilder;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.config.rebind.AbstractAsyncGenerator;
import org.jboss.errai.config.rebind.GenerateAsync;
import org.jboss.hal.config.client.VersionInfo;

@GenerateAsync(VersionInfo.class)
public class VersionInfoGenerator extends AbstractAsyncGenerator {

    private static final String HAL_VERSION = "hal.version";
    private static final String PACKAGE_NAME = VersionInfo.class.getPackage().getName();
    private static final String CLASS_NAME = VersionInfo.class.getSimpleName() + "Impl";
    private static final String VERSION_METHOD_NAME = "getVersion";

    @Override
    protected String generate(final TreeLogger logger, final GeneratorContext context) {
        String halVersion = failSafeGetProperty(context.getPropertyOracle(), HAL_VERSION, "n/a");

        return ClassBuilder.define(PACKAGE_NAME + "." + CLASS_NAME)
                .publicScope().implementsInterface(VersionInfo.class)
                .body()
                .publicMethod(String.class, VERSION_METHOD_NAME)
                .body()
                .append(Stmt.loadLiteral(halVersion).returnValue())
                .finish()
                .toJavaString();
    }

    @Override
    public String generate(final TreeLogger logger, final GeneratorContext context, final String typeName)
            throws UnableToCompleteException {
        return startAsyncGeneratorsAndWaitFor(VersionInfo.class, context, logger, PACKAGE_NAME, CLASS_NAME);
    }
}
