package net.ducquoc.tr.maven.plugin.linecount.engine.executor;

import net.ducquoc.tr.maven.plugin.linecount.engine.walker.LineCountJavaWalker;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Line count executor.
 */
public class LineCountExecutor {

    private Log log;
    private String baseDir;
    private String srcMain;
    private String fileExt;
    private boolean trimPkgNames;
    private boolean display;
    private boolean outcome;
    private Boolean presentNonCode;

    /**
     * No args constructor.
     */
    public LineCountExecutor() {
        super();
    }

    /**
     * All args constructor.
     *
     * @param log
     * @param baseDir
     * @param srcMain
     * @param fileExt
     * @param trimPkgNames
     * @param display
     * @param outcome
     * @param presentNonCode
     */
    public LineCountExecutor(Log log, String baseDir, String srcMain, String fileExt, boolean trimPkgNames,
                             boolean display, boolean outcome, Boolean presentNonCode) {
        this();

        this.log = log;
        this.baseDir = baseDir;
        this.srcMain = srcMain;
        this.fileExt = fileExt;
        this.trimPkgNames = trimPkgNames;
        this.display = display;
        this.outcome = outcome;
        this.presentNonCode = presentNonCode;
    }

    /**
     * Executes.
     *
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {
        try {
            if (Files.exists(Paths.get(baseDir + File.separator + srcMain).toAbsolutePath())) {
                LineCountJavaWalker lineCountJavaWalker = new LineCountJavaWalker(log, baseDir, srcMain, fileExt,
                        trimPkgNames, display, outcome, presentNonCode);

                Files.walkFileTree(Paths.get(baseDir + File.separator + srcMain).toAbsolutePath(),
                        lineCountJavaWalker);
                lineCountJavaWalker.presentOutcome();
            } else {
                log.warn("Does not contain a source directory: " + baseDir + File.separator + srcMain);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

}
