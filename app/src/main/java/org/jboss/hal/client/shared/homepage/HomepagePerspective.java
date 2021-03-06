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
package org.jboss.hal.client.shared.homepage;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.hal.client.resources.NameTokens;
import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.PanelType;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;

/**
 * @author Harald Pehl
 */
@ApplicationScoped
@WorkbenchPerspective(identifier = NameTokens.homepagePerspective, isDefault = true)
public class HomepagePerspective {

    @Perspective
    public PerspectiveDefinition perspective() {
        final PerspectiveDefinition perspective = new PerspectiveDefinitionImpl(PanelType.ROOT_STATIC);
        perspective.setName("Homepage");
        perspective.getRoot().addPart(new PartDefinitionImpl(new DefaultPlaceRequest(NameTokens.homepagePart)));
        return perspective;
    }
}
