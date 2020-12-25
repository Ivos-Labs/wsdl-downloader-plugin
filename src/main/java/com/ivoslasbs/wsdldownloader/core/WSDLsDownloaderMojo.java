/**
 * 
 */
package com.ivoslasbs.wsdldownloader.core;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.ivoslasbs.wsdldownloader.dto.ProxyConf;
import com.ivoslasbs.wsdldownloader.dto.Wsdl;

/**
 * 
 * Class to provide most of the infrastructure required to execute the plugin
 * 
 * @since 1.0.0
 * @author imperezivan
 *
 */
@Mojo(name = "downloadWSDLs", requiresDependencyResolution = ResolutionScope.COMPILE)
public class WSDLsDownloaderMojo extends AbstractMojo {

    /** List of wsdl to be downloaded */
    @Parameter
    private List<Wsdl> wsdls;

    /** HTTP Proxy */
    @Parameter
    private String httpProxy;

    /** HTTPS Proxy */
    @Parameter
    private String httpsProxy;

    /** Base directory */
    @Parameter(defaultValue = "${basedir}", readonly = true)
    public String basedir;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        super.getLog().info("-----------------------------------------------------------------");
        super.getLog().info("wsdl-downloader-maven-plugin starting");
        super.getLog().info("-----------------------------------------------------------------");

        if (this.httpProxy != null && !this.httpProxy.isEmpty()) {
            super.getLog().info("Setting HTTP Proxy " + this.httpProxy);
            ProxyConf httpProxy = new ProxyConf(this.httpProxy);
            Optional.ofNullable(httpProxy.getProxyHost()).ifPresent(v -> System.setProperty("http.proxyHost", v));
            Optional.ofNullable(httpProxy.getProxyPort()).ifPresent(v -> System.setProperty("http.proxyPort", v));
            Optional.ofNullable(httpProxy.getProxyUser()).ifPresent(v -> System.setProperty("http.proxyUser", v));
            Optional.ofNullable(httpProxy.getProxyPassword()).ifPresent(v -> System.setProperty("http.proxyPassword", v));
        }

        if (this.httpsProxy != null && !this.httpsProxy.isEmpty()) {
            super.getLog().info("Setting HTTPS Proxy " + this.httpsProxy);
            ProxyConf httpsProxy = new ProxyConf(this.httpsProxy);
            Optional.ofNullable(httpsProxy.getProxyHost()).ifPresent(v -> System.setProperty("https.proxyHost", v));
            Optional.ofNullable(httpsProxy.getProxyPort()).ifPresent(v -> System.setProperty("https.proxyPort", v));
            Optional.ofNullable(httpsProxy.getProxyUser()).ifPresent(v -> System.setProperty("https.proxyUser", v));
            Optional.ofNullable(httpsProxy.getProxyPassword()).ifPresent(v -> System.setProperty("https.proxyPassword", v));
        }

        this.wsdls.forEach(this::download);

        super.getLog().info("wsdl-downloader-maven-plugin finished");
        super.getLog().info("-----------------------------------------------------------------");

    }

    /**
     * Donwloads a wsdl
     * 
     * @param wsdl DTO with wsdl url, prefix and path
     */
    private void download(Wsdl wsdl) {

        try {

            super.getLog().info("downloading wsdl");
            super.getLog().info("      wsdl: " + wsdl.getUrl());
            super.getLog().info("    prefix: " + wsdl.getPrefix());
            super.getLog().info("      path: " + wsdl.getPath());

            File path = new File(new File(this.basedir), wsdl.getPath());
            WSDLDownloader downloader = new WSDLDownloader(wsdl.getUrl(), wsdl.getPrefix(), path);
            downloader.download();
            super.getLog().info("wsdl downloaded");
            super.getLog().info("-----------------------------------------------------------------");

        } catch (Exception e) {
            this.getLog().error(e);
            throw new RuntimeException(e);
        }
    }

}
