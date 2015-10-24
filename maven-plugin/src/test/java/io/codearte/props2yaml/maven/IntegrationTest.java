package io.codearte.props2yaml.maven;

import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.3.3"})
public class IntegrationTest {

    @Rule
    public final TestResources resources = new TestResources();

    private final MavenRuntime maven;

    public IntegrationTest(MavenRuntimeBuilder builder) throws Exception {
        this.maven = builder.build();
    }

    @Test
    public void testBasic() throws Exception {
        File basedir = resources.getBasedir("basic");
        maven.forProject(basedir)
                .execute("validate", "-Dproperties=sample.properties")
                .assertErrorFreeLog();
        TestResources.assertFilesPresent(basedir, "sample.yml");
    }

}
