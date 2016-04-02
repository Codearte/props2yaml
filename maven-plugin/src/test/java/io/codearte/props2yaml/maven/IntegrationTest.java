package io.codearte.props2yaml.maven;

import io.takari.maven.testing.TestProperties;
import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static java.lang.String.format;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.3.3"})
public class IntegrationTest {

    @Rule
    public final TestResources resources = new TestResources();

    private final TestProperties properties = new TestProperties();

    private final MavenRuntime maven;

    public IntegrationTest(MavenRuntimeBuilder builder) throws Exception {
        this.maven = builder.build();
    }

    @Test
    public void testBasic() throws Exception {
        File basedir = resources.getBasedir("basic");
        String goal = format("io.codearte.props2yaml:props2yaml-maven-plugin:%s:convert", properties.getPluginVersion());
        maven.forProject(basedir)
                .execute(goal, "-Dproperties=sample.properties")
                .assertErrorFreeLog();
        TestResources.assertFilesPresent(basedir, "sample.yml");
    }

}
