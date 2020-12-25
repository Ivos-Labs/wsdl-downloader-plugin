/**
 *
 */
package com.ivoslasbs.wsdldownloader.dto;

/**
 * WSDL data
 *
 * @since 1.0.0
 * @author imperezivan
 *
 */
public class Wsdl {

    /** The WSDL url */
    private String url;

    /** The prefix to be used in file names */
    private String prefix;

    /** The path where will be saved the WSDL */
    private String path;

    /** Creates a Wsdl instance */
    public Wsdl() {
        super();
    }

    /**
     * Gets the url
     *
     * @return {@code String} The url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets the url
     *
     * @param url {@code String} The url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the prefix
     *
     * @return {@code String} The prefix
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Sets the prefix
     *
     * @param prefix {@code String} The prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets the path
     *
     * @return {@code String} The path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the path
     *
     * @param path {@code String} The path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
