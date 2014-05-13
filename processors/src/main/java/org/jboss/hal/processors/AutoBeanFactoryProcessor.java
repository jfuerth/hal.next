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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.jboss.hal.annotations.BeanFactory;
import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;

/**
 * @author Harald Pehl
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("org.jboss.hal.annotations.BeanFactory")
public class AutoBeanFactoryProcessor extends AbstractErrorAbsorbingProcessor {

    static final String PACKAGE_NAME = "org.jboss.hal.model";
    static final String CLASS_NAME = "HalBeanFactory";
    private final Set<String> factories;

    public AutoBeanFactoryProcessor() {factories = new HashSet<>();}

    @Override
    protected boolean processWithExceptions(final Set<? extends TypeElement> annotations,
            final RoundEnvironment roundEnv)
            throws Exception {

        // If prior processing threw an error exit
        if (roundEnv.errorRaised()) {
            return false;
        }

        final Messager messager = processingEnv.getMessager();

        // Collect AutoBean factories
        if (!roundEnv.processingOver()) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BeanFactory.class);
            for (Element element : elements) {
                if (element.getKind() == ElementKind.INTERFACE) {
                    TypeElement classElement = (TypeElement) element;
                    String factory = classElement.getQualifiedName().toString();
                    messager.printMessage(Diagnostic.Kind.NOTE, "Discovered AutoBean factory [" + factory + "]");
                    factories.add(factory);
                } else {
                    String error = String
                            .format("Annotation %s was placed on the class %s, but is restricted to be placed on interfaces only!",
                                    BeanFactory.class.getSimpleName(), element);
                    messager.printMessage(Diagnostic.Kind.ERROR, error);
                    throw new GenerationException(error);
                }
            }
        }

        // Generate composite AutoBean factory
        if (roundEnv.processingOver()) {
            try {
                AutoBeanFactoryGenerator generator = new AutoBeanFactoryGenerator();
                StringBuffer code = generator.generate(factories);
                FileObject mf = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT,
                        PACKAGE_NAME, CLASS_NAME + ".java");
                Writer w = mf.openWriter();
                BufferedWriter bw = new BufferedWriter(w);
                bw.append(code);
                bw.close();
                w.close();

                messager.printMessage(NOTE,
                        "Successfully generated composite AutoBean factory [" + PACKAGE_NAME + "." + CLASS_NAME + "]");
            } catch (GenerationException e) {
                final String msg = e.getMessage();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
            }
        }
        return true;
    }
}
