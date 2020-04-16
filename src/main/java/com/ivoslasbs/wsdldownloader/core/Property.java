/**
 * 
 */
package com.ivoslasbs.wsdldownloader.core;

/**
 * @since
 * @author imperezivan
 *
 */
public class Property {

    /** The key */
    private String Key;

    /** The value */
    private String value;

    /**
     * Crea una instancia Property
     */
    public Property() {
        super();
    }

    /**
     * Crea una instancia Property
     * 
     * @param key
     * @param value
     */
    public Property(String key, String value) {
        super();
        Key = key;
        this.value = value;
    }

    /**
     * Gets the key
     *
     * @return {@code String} The key
     */
    public String getKey() {
        return this.Key;
    }

    /**
     * Sets the key
     *
     * @param key {@code String} The key to set
     */
    public void setKey(String key) {
        Key = key;
    }

    /**
     * Gets the value
     *
     * @return {@code String} The value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value
     *
     * @param value {@code String} The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
