/**
 * 
 */
package com.ivoslasbs.wsdldownloader.core;

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

    /** */
    @Parameter
    private String wsdl;

    /** */
    @Parameter
    private String prefix;

    /** */
    @Parameter
    private String path;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
	super.getLog().info("downloading wsdl");
	super.getLog().info("      wsdl: " + this.wsdl);
	super.getLog().info("    prefix: " + this.prefix);
	super.getLog().info("      path: " + this.path);

	WSDLDownloader downloader = new WSDLDownloader(this.wsdl, this.prefix, this.path);
	downloader.download();
	super.getLog().info("wsdl downloaded");
	super.getLog().info("-----------------------------------------------------------------");

    }

}
