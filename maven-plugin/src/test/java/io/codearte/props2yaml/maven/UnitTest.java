package io.codearte.props2yaml.maven;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static io.takari.maven.testing.TestResources.assertFilesPresent;

public class UnitTest {

    @Rule
    public final TestResources resources = new TestResources();

    @Rule
    public final TestMavenRuntime maven = new TestMavenRuntime();

    @Test
    public void testBasic() throws Exception {
        File basedir = resources.getBasedir("basic");
        maven.executeMojo(basedir, "convert", TestMavenRuntime.newParameter("properties", "sample.properties"));
        assertFilesPresent(basedir, "sample.yml");
    }

    @Test
    public void testPomlessWitProp() throws Exception {
        File basedir = resources.getBasedir("pomless");
        maven.executeMojo(basedir, "convert", TestMavenRuntime.newParameter("properties", "sample.properties"));
        assertFilesPresent(basedir, "sample.yml");
    }

    @Test
    public void testPropertiesWithoutDot() throws Exception {
        File basedir = resources.getBasedir("withoutdot");
        maven.executeMojo(basedir, "convert", TestMavenRuntime.newParameter("properties", "sample"));
        assertFilesPresent(basedir, "sample.yml");
    }

    @Test
    public void testWithAbsoluteDir() throws Exception {
        File basedir = resources.getBasedir("pomless");
        String absolutePath = new File(basedir, "sample.properties").getAbsolutePath();
        maven.executeMojo(basedir, "convert", TestMavenRuntime.newParameter("properties", absolutePath));
        assertFilesPresent(basedir, "sample.yml");
    }
}
