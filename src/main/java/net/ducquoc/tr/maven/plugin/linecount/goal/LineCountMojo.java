package net.ducquoc.tr.maven.plugin.linecount.goal;

import net.ducquoc.tr.maven.plugin.linecount.engine.PluginEngine;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;


/**
 * Goal <strong>linecount</strong>.
 */
@Mojo(
    name = "linecount",
    defaultPhase = LifecyclePhase.VALIDATE,
    requiresDependencyResolution = ResolutionScope.TEST
)
public class LineCountMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    /**
     * name of the root directory for the source files
     * @parameter
     */
    @Parameter(property = "srcMain", defaultValue = "src")
    private String srcMain;

    /**
     * extension of the source files
     * @parameter
     */
    @Parameter(property = "fileExt", defaultValue = "java")
    private String fileExt;

    /**
     * trim package names
     * @parameter
     */
    @Parameter(property = "trimPkgNames", defaultValue = "false")
    private boolean trimPkgNames;

    /**
     * output linecount data to stdout.
     * @parameter
     */
    @Parameter(property = "display", defaultValue = "true")
    private boolean display;

    /**
     * output linecount data to file "linecount.txt".
     * @parameter
     */
    @Parameter(property = "outcome", defaultValue = "false")
    private boolean outcome;

    /**
     * non-code present or not - a bit tricky/dodgy.
     * @parameter
     */
    @Parameter(property = "presentNonCode", defaultValue = "false")
    private boolean presentNonCode;

    public void execute() throws MojoExecutionException {
        try {
            PluginEngine.countLines(getLog(), project.getBasedir().getAbsolutePath(), srcMain,
                    fileExt, trimPkgNames, display, outcome, presentNonCode);
        } catch (MojoExecutionException e) {
            getLog().error(e.getMessage());
        }
    }

}
