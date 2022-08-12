package net.ducquoc.tr.maven.plugin.linecount.engine.walker;

import net.ducquoc.tr.maven.plugin.linecount.engine.PluginEngine;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Visitor pattern to count lines from java source files <strong>*.java</strong>.
 */
public class LineCountJavaWalker extends SimpleFileVisitor<Path> {

    public static final int PACKAGE_INDEX = 5;
    public static final Pattern PACKAGE_DECLARATION = Pattern.compile("(package)(\\s+)(/\\*.*\\*/)?(\\s+)?(.+)(;)");
    public static final String SINGLE_LINE_COMMENT = "//";
    public static final String START_BLOCK_COMMENT = "/*";
    public static final String END_BLOCK_COMMENT = "*/";
    public static final String START_BLOCK_SPECIAL_COMMENT = "/**";

    private Log log;
    private String baseDir;
    private String srcMain;
    private String fileExt;
    private boolean trimPkgNames;
    private boolean display;
    private boolean outcome;
    private Boolean presentNonCode;

    private PathMatcher matcherJava;
    private TreeMap<String, int[]> lcHolder = new TreeMap<>();
    private boolean isBlockComment = false;
    private boolean isBlockDoc = false;
    private boolean isPkgFound = false;
    private String pkgName;

    /**
     * No args constructor.
     */
    public LineCountJavaWalker() {
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
    public LineCountJavaWalker(Log log, String baseDir, String srcMain, String fileExt, boolean trimPkgNames,
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

        this.matcherJava = FileSystems.getDefault().getPathMatcher("glob:*." + fileExt);
    }

    /**
     * If file is a source file, process it.
     *
     * @param file A regular file
     * @return result  FileVisitResult
     */
    public FileVisitResult walkTheWalk(Path file) {
        Path name = file.getFileName();

        if (name != null) {
            Path absolutePathOfFile = file.toAbsolutePath().normalize();

            if (matcherJava.matches(name)) {
                processSource(absolutePathOfFile);
            } else {
                return FileVisitResult.CONTINUE;
            }
        }

        return FileVisitResult.CONTINUE;
    }

    /**
     * Processes source.
     *
     * @param absolutePath
     */
    public void processSource(Path absolutePath) {

        isBlockComment = false;
        isBlockDoc = false;
        isPkgFound = false;

        int fileCounterTotal = 0;
        int fileCounterBlank = 0;
        int fileCounterComment = 0;
        int fileCounterJavaDoc = 0;
        String trimmedLine;

        List<String> lines = PluginEngine.readTextFile(absolutePath);

        fileCounterTotal = lines.size();

        for (String line : lines) {
            if (line == null) {
                continue;
            }
            trimmedLine = line.trim();

            if (isJavaDoc(trimmedLine)) {
                fileCounterJavaDoc++;
            } else if (isComment(trimmedLine)) {
                fileCounterComment++;
            } else if (isBlank(trimmedLine)) {
                fileCounterBlank++;
            } else if (!isPkgFound) {
                if (isPackage(trimmedLine)) {
                    // found package declaration
                }
            }
        }

        lcHolder.put((pkgName == null ? "" : pkgName) + ":" + absolutePath.getFileName() + ":" + srcType(absolutePath),
                new int[]{fileCounterBlank, fileCounterJavaDoc, fileCounterComment,
                        fileCounterTotal - (fileCounterBlank + fileCounterJavaDoc + fileCounterComment)});
    }

    private boolean isBlank(String line) {
        return line == null || line.isEmpty();
    }

    private boolean isComment(String line) {
        if (line.endsWith(END_BLOCK_COMMENT)) {
            if (this.isBlockComment) {
                this.isBlockComment = false;
                return true;
            } else {
                return false;
            }
        } else if (this.isBlockComment) {
            return true;
        } else if (line.startsWith(SINGLE_LINE_COMMENT)) {
            this.isBlockComment = false;
            return true;
        } else if (line.startsWith(START_BLOCK_COMMENT) && !line.contains(END_BLOCK_COMMENT)) {
            this.isBlockComment = true;
            return true;
        } else if (line.startsWith(START_BLOCK_COMMENT) && line.endsWith(END_BLOCK_COMMENT)) {
            this.isBlockComment = false;
            return true;
        } else {
            return false;
        }
    }

    private boolean isJavaDoc(String line) {
        if (line.endsWith(END_BLOCK_COMMENT)) {
            if (this.isBlockDoc) {
                this.isBlockDoc = false;
                return true;
            } else {
                return false;
            }
        } else if (this.isBlockDoc) {
            return true;
        } else if (line.startsWith(START_BLOCK_SPECIAL_COMMENT) && !line.contains(END_BLOCK_COMMENT)) {
            this.isBlockDoc = true;
            return true;
        } else if (line.startsWith(START_BLOCK_SPECIAL_COMMENT) && line.endsWith(END_BLOCK_COMMENT)) {
            this.isBlockDoc = false;
            return true;
        } else {
            return false;
        }
    }

    private boolean isPackage(String line) {
        Matcher matcher = PACKAGE_DECLARATION.matcher(line);
        isPkgFound = matcher.find();

        if (isPkgFound) {
            pkgName = matcher.group(PACKAGE_INDEX);
        }

        return isPkgFound;
    }

    private String srcType(Path path) {
        String pathStr = path.toString();

        if (pathStr.contains("/test/") && !pathStr.contains("src/main/")) {
            return "test";
        } else if (pathStr.contains("/integration-test/") || pathStr.contains("/integrationTest/")) {
            return "int-test";
        } else {
            return "src";
        }
    }

    /**
     * Presents the outcome and display on demand.
     */
    public void presentOutcome() {
        String data = PluginEngine.processLcData(lcHolder, fileExt, trimPkgNames, presentNonCode).toString();

        if (display) {
            if (data.length() > 0) {
                log.info(String.format("LC - directory: %s\n%s", (baseDir + File.separator + srcMain), data));
            } else {
                log.warn("Does not have source files: " + (baseDir + File.separator + srcMain) + " : *." + fileExt);
            }
        }

        if (outcome) {
            File file = new File(baseDir + File.separator + PluginEngine.OUTPUT_LINECOUNT_FILE);

            try {
                FileWriter writer = new FileWriter(file);
                writer.append(data);
                writer.flush();
            } catch (IOException ioEx) {
                log.error(ioEx.getMessage());
            }
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile()) {
            return walkTheWalk(file);
        } else {
            log.warn("ignored - not a regular file: " + file);
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        log.error(e.getMessage());
        return FileVisitResult.CONTINUE;
    }

}
