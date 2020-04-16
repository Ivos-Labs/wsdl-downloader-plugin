/**
 * 
 */
package com.ivoslasbs.wsdldownloader.core;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * @author www.ivoslabs.com
 *
 */
@Mojo(name = "downloadWSDL", requiresDependencyResolution = ResolutionScope.COMPILE)
public class Process extends AbstractMojo {

    /** List of wsdl to be downloaded */
    @Parameter
    private List<Wsdl> wsdls;

    /** List of java properties */
    @Parameter
    private List<Property> properties;

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

        if (this.properties != null && !this.properties.isEmpty()) {
            super.getLog().info("Setting properties");
            Consumer<Property> printProp = prop -> this.getLog().info("    Setting property " + prop.getKey() + ":" + prop.getValue());
            this.properties.stream().peek(printProp).forEach(prop -> System.setProperty(prop.getKey(), prop.getValue()));
            super.getLog().info("Properties set");
        }

        try {
            this.wsdls.forEach(this::download);
        } catch (Exception e) {
            this.getLog().error(e);
            throw new RuntimeException(e);
        }

        super.getLog().info("wsdl-downloader-maven-plugin finished");
        super.getLog().info("-----------------------------------------------------------------");

    }

    /**
     * Donwload a wsdl
     * 
     * @param wsdl DTO with wsdl url, prefix and path
     */
    private void download(Wsdl wsdl) {

        super.getLog().info("downloading wsdl");
        super.getLog().info("      wsdl: " + wsdl.getUrl());
        super.getLog().info("    prefix: " + wsdl.getPrefix());
        super.getLog().info("      path: " + wsdl.getPath());

        File path = new File(new File(this.basedir), wsdl.getPath());
        WSDLDownloader downloader = new WSDLDownloader(wsdl.getUrl(), wsdl.getPrefix(), path);
        downloader.download();
        super.getLog().info("wsdl downloaded");
        super.getLog().info("-----------------------------------------------------------------");
    }

}
