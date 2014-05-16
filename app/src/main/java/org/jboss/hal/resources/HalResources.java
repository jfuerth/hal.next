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
package org.jboss.hal.resources;

import com.github.gwtbootstrap.client.ui.resources.Resources;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

/**
 * @author Harald Pehl
 */
public interface HalResources extends Resources {

    // ------------------------------------------------------ HAL Resources

    @CssResource.NotStrict
    @Source("css/community.css")
    CssResource community();

    @CssResource.NotStrict
    @Source("css/product.css")
    CssResource product();

    @CssResource.NotStrict
    @Source("prettyprint/prettify.css")
    CssResource prettifyCss();

    @Source("prettyprint/prettify.js")
    TextResource prettifyJs();

    @Source("js/lunr.js")
    TextResource lunrJs();

    @Source("js/mousetrap.js")
    TextResource mousetrapJs();

    @Source("progress/progress-polyfill.js")
    TextResource progressPolyfillJs();

    @CssResource.NotStrict
    @Source("progress/progress-polyfill.css")
    CssResource progressPolyfillCss();


    // ------------------------------------------------------ Overridden GWT Bootstrap Resources

    @Override
    @Source("css/bootstrap.min.css")
    TextResource bootstrapCss();
}
