package net.ducquoc.tr.maven.plugin.linecount.engine;

import net.ducquoc.tr.maven.plugin.linecount.engine.executor.LineCountExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

/**
 * PluginEngine. Copy from Internet (MIT license).
 */
public class PluginEngine {

    public final static int MIN_HEADER_LEN = 16;
    public final static String OUTPUT_LINECOUNT_FILE = "linecount.txt";

    private PluginEngine() {
        // no instance required, use static factory methods
    }

    /**
     * Count lines.
     *
     * @param log
     * @param rootDir
     * @param srcMain
     * @param fileExt
     * @param trimPkgNames
     * @param display
     * @param outcome
     * @param presentNonCode
     * @throws MojoExecutionException
     */
    public static void countLines(Log log, String rootDir, String srcMain, String fileExt, boolean trimPkgNames,
                                  boolean display, boolean outcome, Boolean... presentNonCode)
            throws MojoExecutionException {
        LineCountExecutor lineCountExecutor = new LineCountExecutor(log, rootDir, srcMain, fileExt, trimPkgNames,
                display, outcome,
                presentNonCode != null && presentNonCode.length > 0 && Boolean.TRUE.equals(presentNonCode[0]));
        lineCountExecutor.execute();
    }

    /**
     * Reads text file.
     *
     * @param absolutePath
     * @return
     */
    public static List<String> readTextFile(Path absolutePath) {
        File file = new File(absolutePath.toString());
        List<String> lines = new ArrayList<>();

        if (file.exists() && file.isFile() && file.canRead()) {
            try (InputStream inputStream = new FileInputStream(file);
                 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lines;
    }

    /**
     * Processes line-of-code data.
     *
     * @param locData
     * @param fileExt
     * @param trimPkgNames
     * @param presentNonCode
     * @return
     */
    public static StringBuilder processLcData(TreeMap<String, int[]> locData, String fileExt, boolean trimPkgNames,
                                              Boolean... presentNonCode) {
        StringBuilder sb = new StringBuilder();

        if (locData.size() > 0) {
            SortedSet<String> keys = new TreeSet<>(locData.keySet());

            Set<String> packageData = new TreeSet<>();
            int longestPName = 0;
            int longestCName = 0;

            for (String key : keys) {
                String[] fields = key.split(":");

                String packageName = fields[0];
                String className = fields[1];

                packageData.add(packageName);

                longestPName = Math.max(packageName.length(), longestPName);
                longestCName = Math.max(className.length(), longestCName);
            }

            String[] packageNames = packageData.stream().toArray(String[]::new);

            String commonPackage = "";

            if (trimPkgNames) {
                commonPackage = StringUtils.getCommonPrefix(packageNames);

                // if all package names are identical, trim the last part of the common package name
                if (commonPackage.length() == longestPName) {
                    commonPackage = PluginEngine.trimPackageName(commonPackage);
                }

                final String tempCommonPackage = commonPackage;
                commonPackage = Arrays.stream(packageNames)
                        .filter(s -> s.replace(tempCommonPackage, "").isEmpty())
                        .findFirst().orElse(PluginEngine.trimPackageName(commonPackage));
            }

            String packageLine = packageData.size() + " package(s)";
            String classLine = locData.size() + " file(s)";

            int headerP = Math.max(longestPName - commonPackage.length(), packageLine.length());
            int headerC = Math.max(longestCName, classLine.length());

            // MIN_HEADER_LEN is longer than the minimum headers.: "1 package(s)" & "1 file(s)"
            headerP = Math.max(MIN_HEADER_LEN, headerP);
            headerC = Math.max(MIN_HEADER_LEN, headerC);

            Boolean nonCodePresent = presentNonCode != null && presentNonCode.length > 0
                    && Boolean.TRUE.equals(presentNonCode[0]);
            String lineHeader = String.format("+%0" + (headerP + 2) + "d+%0" + (headerC + 2)
                    + "d+%010d+%010d+%010d+%010d+%010d+%010d+\n", 0, 0, 0, 0, 0, 0, 0, 0).replace('0', '-');
            if (nonCodePresent) {
                lineHeader = String.format("+%0" + (headerP + 2) + "d+%0" + (headerC + 2)
                        + "d+%010d+%010d+%010d+\n", 0, 0, 0, 0, 0).replace('0', '-');
            }

            int[] totals = new int[5];

            sb.append(lineHeader);
            if (nonCodePresent) {
                sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                + "s | %-8s | %-8s | %-8s |\n",
                        "Package Name", "File Name", "Type", "Code", "Total"));
            } else {
                sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                + "s | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s |\n",
                        "Package Name", "File Name", "Type", "Blank", "JavaDoc", "Comment", "Code", "Total"));
            }
            sb.append(lineHeader);

            String[] fields = locData.firstKey().split(":");

            String commonPackageName = trimPkgNames ? fields[0].replace(commonPackage, "") : fields[0];

            for (String key : keys) {
                int[] counters = locData.get(key);
                int total = IntStream.of(counters).sum();

                fields = key.split(":");

                String packageName = trimPkgNames ? fields[0].replace(commonPackage, "") : fields[0];
                String className = fields[1];
                String classType = fields[2];

                if (!packageName.equals(commonPackageName)) {
                    sb.append(lineHeader);
                    commonPackageName = packageName;
                }

                if (nonCodePresent) {
                    sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                    + "s | %-8s | %8d | %8d |\n", packageName, className,
                            classType, counters[3], total));
                } else {
                    sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                    + "s | %-8s | %8d | %8d | %8d | %8d | %8d |\n", packageName, className,
                            classType, counters[0], counters[1], counters[2], counters[3], total));
                }

                totals[0] += counters[0];
                totals[1] += counters[1];
                totals[2] += counters[2];
                totals[3] += counters[3];
                totals[4] += total;
            }

            sb.append(lineHeader);
            if (nonCodePresent) {
                sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                + "s | %-8s | %8d | %8d |\n", packageLine, classLine,
                        fileExt, totals[0], totals[4]));
            } else {
                sb.append(String.format("| %-" + headerP + "s | %-" + headerC
                                + "s | %-8s | %8d | %8d | %8d | %8d | %8d |\n", packageLine, classLine,
                        fileExt, totals[0], totals[1], totals[2], totals[3], totals[4]));
            }
            sb.append(lineHeader);
        }

        return sb;
    }

    /**
     * Trims package name.
     *
     * @param packageName
     * @return
     */
    public static String trimPackageName(String packageName) {
        int last = packageName.lastIndexOf(".");
        return packageName.substring(0, last + 1);
    }

    /**
     * Removes array element.
     *
     * @param src
     * @param index
     * @return
     */
    public static String[] removeArrayElement(String[] src, int index) {
        List<String> dst = new LinkedList<String>(Arrays.asList(src));
        dst.remove(index);
        return dst.toArray(new String[src.length - 1]);
    }

}
