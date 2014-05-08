package org.jboss.hal.config.client;

import java.util.List;

/**
 * Instance holding product related information. An instance of this interface is generated using defered binding.
 */
public interface ProductInfo {
    /**
     * Whether this is the community or product version.
     *
     * @return the profile
     */
    Profile getProfile();

    /**
     * The product title from the management model
     *
     * @return the product title
     */
    String getProductName();

    /**
     * The configured locales in the GWT module.
     *
     * @return the list of supported locales
     */
    List<String> getLocales();

    public enum Profile {COMMUNITY, PRODUCT}
}
