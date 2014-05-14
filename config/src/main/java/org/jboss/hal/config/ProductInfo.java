package org.jboss.hal.config;

import java.util.List;

/**
 * Instance holding product related information. An instance of this interface is generated using deferred binding.
 */
public interface ProductInfo {
    /**
     * Whether this is the community or product version. Taken from the GWT module, read-only.
     */
    Variant getHalVariant();

    /**
     * The console version. Taken from the GWT module, read-only.
     */
    String getHalVersion();

    /**
     * The configured locales in the GWT module. Taken from the GWT module, read-only.
     *
     * @return the list of supported locales
     */
    List<String> getLocales();

    /**
     * WildFly / EAP version. Taken from the management model, read/write.
     */
    String getProductVersion();

    /**
     * The product title. Taken from the management model, read/write.
     */
    String getProductName();

    /**
     * Update writable properties with values from the management model.
     */
    void update(String productVersion, String productName);

    public enum Variant {COMMUNITY, PRODUCT}
}
