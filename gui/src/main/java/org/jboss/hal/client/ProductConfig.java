package org.jboss.hal.client;

import java.util.List;

/**
 * Instance holding product related information. An instance of this interface is generated using defered binding.
 */
public interface ProductConfig {

    /**
     * Whether this is the community or product version.
     *
     * @return the profile
     */
    Profile getProfile();

    /**
     * The version of the HAL release stream
     *
     * @return the HAL version
     */
    String getConsoleVersion();

    /**
     * The product title from the management model
     *
     * @return the product title
     */
    String getProductName();

    /**
     * The product version from the management model
     *
     * @return the product version
     */
    String getProductVersion();

    /**
     * The hostname / ip address of the dev host (only relevant in dev mode).
     *
     * @return the hostname
     */
    String getDevHost();

    /**
     * The configured locales in the GWT module.
     *
     * @return the list of supported locales
     */
    List<String> getLocales();

    public enum Profile {COMMUNITY, PRODUCT}
}
