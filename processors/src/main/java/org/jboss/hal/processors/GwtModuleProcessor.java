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

import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;

/**
 * @author Harald Pehl
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class GwtModuleProcessor extends AbstractErrorAbsorbingProcessor {

    private final GwtModuleGenerator generator;

    public GwtModuleProcessor() {
        GwtModuleGenerator gen = null;
        try {
            gen = new GwtModuleGenerator();
        } catch (Exception e) {
            rememberInitializationError(e);
        }
        generator = gen;
    }

    @Override
    protected boolean processWithExceptions(final Set<? extends TypeElement> annotations,
            final RoundEnvironment roundEnv) throws Exception {

        // We don't have any post-processing
        if (roundEnv.processingOver()) {
            return false;
        }

        // If prior processing threw an error exit
        if (roundEnv.errorRaised()) {
            return false;
        }

        final Messager messager = processingEnv.getMessager();
        final String packageName = "org.jboss.hal.app";
        for (GwtModule gwtModule : GwtModule.values()) {
            StringBuffer code = generator
                    .generate("org.jboss.hal.app", null, gwtModule.module(), null, processingEnv);
            writeCode(packageName, gwtModule.module(), code);
        }
        return true;
    }
}
