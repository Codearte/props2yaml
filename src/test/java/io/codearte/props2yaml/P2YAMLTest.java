package io.codearte.props2yaml;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class P2YAMLTest {

    @Test
    public void shouldReadSingleLineProperty() throws Exception {
        String yaml = new Props2YAML("a.b=c").convert();

        assertThat(yaml).contains("a:");
        assertThat(yaml).contains("b: c");
    }

    @Test
    public void shouldReadDuplicatedProperties() throws Exception {
        String yaml = new Props2YAML("a=1\na=2").convert();
        System.out.println(yaml);
        assertThat(yaml).contains("a: 2");
    }

    @Test
    public void shouldReadBooleanProperty() throws Exception {
        String yaml = new Props2YAML("a=true").convert();
        System.out.println(yaml);
        assertThat(yaml).contains("a: true");
    }

    @Test
    public void shouldConvertProperties() throws Exception {
        String properties = new String(Files.readAllBytes(Paths.get("src/test/resources/sample.properties")));
        String yaml = new Props2YAML(properties).convert();
        System.out.println(yaml);

        assertThat(yaml).contains("key with spaces: This is the value that could be looked up with the key \"key with spaces\".");
        assertThat(yaml).contains("message: Welcome to Wikipedia!");
        assertThat(yaml).contains("tab: \"\\t\"");
        assertThat(yaml).contains("website: http://en.wikipedia.org/");
    }

}
