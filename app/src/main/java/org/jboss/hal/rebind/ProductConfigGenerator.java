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
package org.jboss.hal.rebind;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.i18n.rebind.LocaleUtils;
import com.google.gwt.i18n.shared.GwtLocale;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class ProductConfigGenerator extends Generator {

    /**
     * Simple name of class to be generated
     */
    private String className = null;
    /**
     * Package name of class to be generated
     */
    private String packageName = null;

    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException {
        TypeOracle typeOracle = context.getTypeOracle();

        try {
            // get classType and save instance variables
            JClassType classType = typeOracle.getType(typeName);
            packageName = classType.getPackage().getName();
            className = classType.getSimpleSourceName() + "Impl";

            // Generate class source code
            generateClass(logger, context);

        } catch (Throwable e) {
            // record to logger that Map generation threw an exception
            e.printStackTrace(System.out);
            logger.log(TreeLogger.ERROR, "Failed to generate product config", e);
        }

        // return the fully qualified name of the class generated
        return packageName + "." + className;
    }

    /**
     * Generate source code for new class. Class extends
     * <code>HashMap</code>.
     *
     * @param logger  Logger object
     * @param context Generator context
     */
    private void generateClass(TreeLogger logger, GeneratorContext context) throws Throwable {

        // get print writer that receives the source code
        PrintWriter printWriter = context.tryCreate(logger, packageName, className);

        // print writer if null, source code has ALREADY been generated, return
        if (printWriter == null) { return; }

        // init composer, set class properties, create source writer
        ClassSourceFileComposerFactory composerFactory =
                new ClassSourceFileComposerFactory(packageName, className);

        // Imports
        composerFactory.addImport("org.jboss.as.console.client.Console");
        composerFactory.addImport("org.jboss.as.console.client.ProductConfig");

        composerFactory.addImport("java.util.*");

        // Interfaces
        composerFactory.addImplementedInterface("org.jboss.as.console.client.ProductConfig");

        // SourceWriter
        SourceWriter sourceWriter = composerFactory.createSourceWriter(context, printWriter);

        // ctor
        generateConstructor(sourceWriter);

        // Methods
        generateMethods(logger, sourceWriter, context);

        // close generated class
        sourceWriter.outdent();
        sourceWriter.println("}");

        // commit generated class
        context.commit(logger, printWriter);
    }

    private void generateConstructor(SourceWriter sourceWriter) {
        // start constructor source generation
        sourceWriter.println("public " + className + "() { ");
        sourceWriter.indent();
        sourceWriter.println("super();");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private void generateMethods(final TreeLogger logger, SourceWriter sourceWriter, GeneratorContext context) throws Throwable {
        PropertyOracle propertyOracle = context.getPropertyOracle();

        // console.profile - mandatory
        String consoleProfile = failFastGetProperty(propertyOracle, "console.profile");
        sourceWriter.println("public ProductConfig.Profile getProfile() { ");
        sourceWriter.indent();
        if ("product".equals(consoleProfile)) {
            sourceWriter.println("return ProductConfig.Profile.PRODUCT;");
        } else if ("community".equals(consoleProfile)) {
            sourceWriter.println("return ProductConfig.Profile.COMMUNITY;");
        } else {
            throw new BadPropertyValueException(
                    "Invalid value for 'console.profile'. Valid values are 'community' or 'product'!");
        }
        sourceWriter.outdent();
        sourceWriter.println("}");

        // console.version - mandatory
        String consoleVersion = failSafeGetProperty(propertyOracle, "console.version", null);
        sourceWriter.println("public String getConsoleVersion() { ");
        sourceWriter.indent();
        if (consoleVersion == null) {
            sourceWriter.println("return null;");
        } else {
            sourceWriter.println("return \"%s\";", consoleVersion);
        }
        sourceWriter.outdent();
        sourceWriter.println("}");

        // getProductName() - delegates to the BootstrapContext
        sourceWriter.println("public String getProductName() { ");
        sourceWriter.indent();
        sourceWriter.println("return org.jboss.as.console.client.Console.MODULES.getBootstrapContext().getProductName();");
        sourceWriter.outdent();
        sourceWriter.println("}");

        // getProductVersion() - delegates to the BootstrapContext
        sourceWriter.println("public String getProductVersion() { ");
        sourceWriter.indent();
        sourceWriter.println("return org.jboss.as.console.client.Console.MODULES.getBootstrapContext().getProductVersion();");
        sourceWriter.outdent();
        sourceWriter.println("}");

        // console.dev.host - mandatory
        String devHost = failFastGetProperty(propertyOracle, "console.dev.host");
        sourceWriter.println("public String getDevHost() { ");
        sourceWriter.indent();
        sourceWriter.println("return \"" + devHost + "\";");
        sourceWriter.outdent();
        sourceWriter.println("}");

        // console.dev.host - mandatory
        LocaleUtils localeUtils = LocaleUtils.getInstance(logger, context.getPropertyOracle(), context);
        Set<GwtLocale> locales = localeUtils.getAllCompileLocales();
        sourceWriter.println("public List<String> getLocales() { ");
        sourceWriter.indent();
        sourceWriter.println("List<String> locales = new LinkedList<String>();");
        for (GwtLocale locale : locales) {
            if (locale.getAsString() != null && locale.getAsString().length() != 0) {
                sourceWriter.println("locales.add(\"" + locale.getAsString() + "\");");
            }
        }
        sourceWriter.println("return locales;");
        sourceWriter.outdent();
        sourceWriter.println("}");
    }

    private String failFastGetProperty(PropertyOracle propertyOracle, String name) throws BadPropertyValueException {
        ConfigurationProperty property = propertyOracle.getConfigurationProperty(name);
        if (property != null) {
            List<String> values = property.getValues();
            if (values != null && !values.isEmpty()) {
                return values.get(0);
            } else {
                throw new BadPropertyValueException("Missing configuration property '" + name + "'!");
            }
        } else {
            throw new BadPropertyValueException("Missing configuration property '" + name + "'!");
        }
    }

    private String failSafeGetProperty(PropertyOracle propertyOracle, String name, String defaultValue) {
        String value = defaultValue;
        try {
            ConfigurationProperty property = propertyOracle.getConfigurationProperty(name);
            if (property != null) {
                List<String> values = property.getValues();
                if (values != null && !values.isEmpty()) {
                    value = values.get(0);
                }
            }
        } catch (BadPropertyValueException e) {
            // ignore and return defaul value
        }
        return value;
    }
}
