/**
 * 
 */
package com.ivoslasbs.wsdldownloader.core;

import java.util.List;

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

    /** The wsdls */
    @Parameter
    private List<Wsdl> wsdls;

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

	try {
	    this.wsdls.forEach(this::download);
	} catch (Exception e) {
	    this.getLog().error(e);
	}

	super.getLog().info("wsdl-downloader-maven-plugin finished");
	super.getLog().info("-----------------------------------------------------------------");

    }

    /**
     * Donwload the a wsdl
     * 
     * @param wsdl DTO with wsdl url, prefix and path
     */
    private void download(Wsdl wsdl) {

	super.getLog().info("downloading wsdl");
	super.getLog().info("      wsdl: " + wsdl.getUrl());
	super.getLog().info("    prefix: " + wsdl.getPrefix());
	super.getLog().info("      path: " + wsdl.getPath());

	WSDLDownloader downloader = new WSDLDownloader(wsdl.getUrl(), wsdl.getPrefix(), wsdl.getPath());
	downloader.download();
	super.getLog().info("wsdl downloaded");
	super.getLog().info("-----------------------------------------------------------------");
    }

}
