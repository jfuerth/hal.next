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
package org.jboss.hal.config;

import java.util.List;

/**
 * Implementation of this class is created by the {@code ProductInfoGenerator}.
 *
 * @see org.jboss.hal.config.rebind.ProductInfoGenerator
 * @author Harald Pehl
 */
public abstract class AbstractProductInfo implements ProductInfo {

    private final Variant variant;
    private final String halVersion;
    private final List<String> locales;
    private String productVersion;
    private String productName;

    protected AbstractProductInfo(final Variant variant, final String halVersion, final List<String> locales) {
        this.variant = variant;
        this.halVersion = halVersion;
        this.locales = locales;
        this.productVersion = "n/a";
        this.productName = "n/a";
    }

    @Override
    public Variant getHalVariant() {
        return variant;
    }

    @Override
    public String getHalVersion() {
        return halVersion;
    }

    @Override
    public List<String> getLocales() {
        return locales;
    }

    @Override
    public String getProductVersion() {
        return productVersion;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void update(final String productVersion, final String productName) {
        this.productVersion = productVersion;
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "profile=" + variant +
                ", consoleVersion='" + halVersion + '\'' +
                ", locales=" + locales +
                ", productVersion='" + productVersion + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
