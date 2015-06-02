package io.codearte.p2yaml;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class P2YAMLTest {

    @Test
    public void shouldReadSingleLineProperty() throws Exception {
        String yaml = new P2YAML("a.b=c").toYAML();

        assertThat(yaml).contains("a:");
        assertThat(yaml).contains("b: c");
    }

    @Test
    public void shouldReadDuplicatedProperties() throws Exception {
        String yaml = new P2YAML("a=1\na=2").toYAML();
        System.out.println(yaml);
        assertThat(yaml).contains("a: 2");
    }

    @Test
    public void shouldReadBooleanProperty() throws Exception {
        String yaml = new P2YAML("a=true").toYAML();
        System.out.println(yaml);
        assertThat(yaml).contains("a: true");
    }

    @Test
    public void shouldConvertProperties() throws Exception {
        String properties = new String(Files.readAllBytes(Paths.get("src/test/resources/sample.properties")));
        String yaml = new P2YAML(properties).toYAML();
        System.out.println(yaml);

        assertThat(yaml).contains("key with spaces: This is the value that could be looked up with the key \"key with spaces\".");
        assertThat(yaml).contains("message: Welcome to Wikipedia!");
        assertThat(yaml).contains("tab: \"\\t\"");
        assertThat(yaml).contains("website: http://en.wikipedia.org/");
    }

}
