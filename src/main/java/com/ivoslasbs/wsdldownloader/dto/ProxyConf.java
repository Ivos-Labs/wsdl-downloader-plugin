/**
 *
 */
package com.ivoslasbs.wsdldownloader.dto;

/**
 * Proxy configuration
 *
 * @since 1.0.0
 * @author imperezivan
 *
 */
public class ProxyConf {

    /** Proxy host */
    private String proxyHost;

    /** Proxy port */
    private String proxyPort;

    /** Proxy user */
    private String proxyUser;

    /** Proxy password */
    private String proxyPassword;

    /**
     * Creates an ProxyConf instance
     *
     * @param proxy proxy example DOMAIN\USER:PWD@10.204.14.44:8080
     */
    public ProxyConf(String proxy) {
        super();

        String usrPwd = null;
        String hostPort;

        int atIdx = proxy.indexOf("@");

        if (atIdx != -1) {
            // when the proxy has user
            usrPwd = proxy.substring(0, atIdx);
            hostPort = proxy.substring(atIdx + 1);
        } else {
            hostPort = proxy;
        }

        if (usrPwd != null) {
            // when the proxy has user
            int col = usrPwd.indexOf(":");
            if (col != -1) {
                // when the proxy has user and password
                this.proxyUser = usrPwd.substring(0, col);
                this.proxyPassword = usrPwd.substring(col + 1);
            } else {
                // when the proxy only has user
                this.proxyUser = usrPwd;
            }
        }

        int col = hostPort.indexOf(":");
        if (col != -1) {
            // when the proxy has host and port
            this.proxyHost = hostPort.substring(0, col);
            this.proxyPort = hostPort.substring(col + 1);
        } else {
            // when the proxy only host
            this.proxyHost = hostPort;
        }

    }

    /**
     * Gets the proxyHost
     *
     * @return {@code String} The proxyHost
     */
    public String getProxyHost() {
        return this.proxyHost;
    }

    /**
     * Gets the proxyPort
     *
     * @return {@code String} The proxyPort
     */
    public String getProxyPort() {
        return this.proxyPort;
    }

    /**
     * Gets the proxyUser
     *
     * @return {@code String} The proxyUser
     */
    public String getProxyUser() {
        return this.proxyUser;
    }

    /**
     * Gets the proxyPassword
     *
     * @return {@code String} The proxyPassword
     */
    public String getProxyPassword() {
        return this.proxyPassword;
    }

}
