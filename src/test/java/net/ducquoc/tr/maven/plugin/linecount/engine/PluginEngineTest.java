package net.ducquoc.tr.maven.plugin.linecount.engine;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test. Might read from folder not in src.
 */
public class PluginEngineTest extends AbstractMojo {

    @Test
    public void testPackageName() throws Exception {
        final String packageName = "net.ducquoc.commandline";
        final String expected = "net.ducquoc.";
        final String actual = PluginEngine.trimPackageName(packageName);

        Assert.assertEquals("Trim package name - failure", expected, actual);

        final String commonPackageName = "net.ducquoc.automata.animation";
        final String commonExpected = "net.ducquoc.automata.";
        final String commonActual = PluginEngine.trimPackageName(commonPackageName);

        Assert.assertEquals("Trim common package name - failure", commonExpected, commonActual);
    }
/*
    @Test
    public void testCountLines() {
        Path baseDir = Paths.get(new File(".").getAbsolutePath(), "java-dtest").normalize();
        Path resDir = Paths.get(".", "src/test/resources").normalize();
        String srcMain = "src";
        String fileExt = "java";
        boolean trimPkgNames = System.getProperty("trimPkgNames") != null;
        boolean display = true;
        boolean outcome = false;
        boolean trickyPresentNonCode = "false".equals(System.getProperty("presentNonCode"));

        try {
            // capture output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream stdOut = System.out;
            System.setOut(ps);

            PluginEngine.countLines(getLog(), baseDir.toString(), srcMain, fileExt, trimPkgNames, display,
                    outcome, trickyPresentNonCode);

            System.setOut(stdOut);

            String[] output = baos.toString().split("\n");

            if (output.length > 0) {
                String[] table = PluginEngine.removeArrayElement(output, 0);
                String actual = String.join("\n", table);

                System.out.println("\nACTUAL OUTCOME:\n");
                System.out.println(actual + "\n");

                String expected = String.join("\n", PluginEngine.readTextFile(
                        Paths.get(resDir.toString(), "linecount.txt")));
                System.out.println("EXPECTED OUTCOME:\n");
                System.out.println(expected + "\n");

                Assert.assertEquals("LineCount - failure :( ", expected, actual);
            } else {
                Assert.fail("No outcome :( ");
            }
        } catch (MojoExecutionException mojoEx) {
            getLog().error(mojoEx.getMessage());
        }
    }
*/
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // not used
    }

}
