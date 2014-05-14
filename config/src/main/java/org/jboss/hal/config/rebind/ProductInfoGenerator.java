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

import static org.jboss.hal.config.ProductInfo.Variant;
import static org.jboss.hal.config.rebind.GeneratorUtils.failSafeGetProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.i18n.rebind.LocaleUtils;
import com.google.gwt.i18n.shared.GwtLocale;
import org.jboss.errai.codegen.builder.impl.ClassBuilder;
import org.jboss.errai.codegen.util.Stmt;
import org.jboss.errai.config.rebind.AbstractAsyncGenerator;
import org.jboss.errai.config.rebind.GenerateAsync;
import org.jboss.hal.config.AbstractProductInfo;
import org.jboss.hal.config.ProductInfo;

@GenerateAsync(ProductInfo.class)
public class ProductInfoGenerator extends AbstractAsyncGenerator {

    private static final String PACKAGE_NAME = ProductInfo.class.getPackage().getName();
    private static final String CLASS_NAME = ProductInfo.class.getSimpleName() + "Impl";

    @Override
    protected String generate(final TreeLogger logger, final GeneratorContext context) {

        String halVersion = failSafeGetProperty(context.getPropertyOracle(), "hal.version", "n/a");
        String variantValue = failSafeGetProperty(context.getPropertyOracle(), "hal.variant", "COMMUNITY")
                .toUpperCase();
        Variant variant;
        try {
            variant = Variant.valueOf(variantValue);
        } catch (IllegalArgumentException e) {
            variant = Variant.COMMUNITY;
        }
        LocaleUtils localeUtils = LocaleUtils.getInstance(logger, context.getPropertyOracle(), context);
        Set<GwtLocale> locales = localeUtils.getAllCompileLocales();
        List<String> localeValues = new ArrayList<>();
        for (GwtLocale locale : locales) {
            if (locale.getAsString() != null && locale.getAsString().length() != 0) {
                localeValues.add(locale.getAsString());
            }
        }

        return ClassBuilder.define(PACKAGE_NAME + "." + CLASS_NAME, AbstractProductInfo.class)
                .publicScope().implementsInterface(ProductInfo.class)
                .body()
                .publicConstructor().callSuper(Stmt.loadLiteral(variant), Stmt.loadLiteral(halVersion),
                        Stmt.loadLiteral(localeValues)).finish()
                .toJavaString();
    }

    @Override
    public String generate(final TreeLogger logger, final GeneratorContext context, final String typeName)
            throws UnableToCompleteException {
        return startAsyncGeneratorsAndWaitFor(ProductInfo.class, context, logger, PACKAGE_NAME, CLASS_NAME);
    }
}
