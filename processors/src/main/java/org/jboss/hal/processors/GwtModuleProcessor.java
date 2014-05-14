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

import static javax.tools.Diagnostic.Kind.NOTE;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;

/**
 * @author Harald Pehl
 */
// We need some type of annotation here. Since the processor does not use the annotation,
// we choose EntryPoint, which is at least semantically close to what we want to do.
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions({"gwt.hal.version", "gwt.dev.host", "gwt.dev.port"})
@SupportedAnnotationTypes("org.jboss.errai.ioc.client.api.EntryPoint")
public class GwtModuleProcessor extends AbstractErrorAbsorbingProcessor {

    static final String PACKAGE_NAME = "org.jboss.hal.app";
    static final String GWT_PREFIX = "gwt.";

    private final GwtModuleGenerator generator;

    public GwtModuleProcessor() {
        GwtModuleGenerator gen = null;
        try {
            gen = new GwtModuleGenerator();
        } catch (Exception e) {
            rememberInitializationError(e);
        }
        this.generator = gen;
    }

    @Override
    protected boolean processWithExceptions(final Set<? extends TypeElement> annotations,
            final RoundEnvironment roundEnv) throws Exception {

        // If prior processing threw an error exit
        if (roundEnv.errorRaised()) {
            return false;
        }

        if (roundEnv.processingOver()) {
            final Messager messager = processingEnv.getMessager();

            Map<String, String> gwtProperties = new HashMap<>();
            Map<String, String> options = processingEnv.getOptions();
            for (String key : options.keySet()) {
                if (key.startsWith(GWT_PREFIX)) {
                    String gwtProperty = key.substring(GWT_PREFIX.length());
                    messager.printMessage(NOTE, "Discovered GWT property [" + gwtProperty + "]");
                    gwtProperties.put(gwtProperty, options.get(key));
                }
            }

            for (GwtModule gwtModule : GwtModule.values()) {
                try {
                    StringBuffer code = generator.generate(gwtModule, gwtProperties);
                    FileObject mf = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
                            PACKAGE_NAME, gwtModule.module());
                    Writer w = mf.openWriter();
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.append(code);
                    bw.close();
                    w.close();

                    messager.printMessage(NOTE, "Successfully generated GWT module [" + gwtModule.module() + "]");
                } catch (GenerationException e) {
                    final String msg = e.getMessage();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
                }
            }
        }
        return true;
    }
}
