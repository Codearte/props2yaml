package io.codearte.props2yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Properties;

public class Props2YAML {

    private final Properties properties = new Properties();

    Props2YAML(String source) {
        try {
            properties.load(new StringReader(source));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Props2YAML(File file) {
        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Props2YAML fromContent(String content) {
        return new Props2YAML(content);
    }

    public static Props2YAML fromFile(File file) {
        return new Props2YAML(file);
    }

    public static Props2YAML fromFile(Path path) {
        return new Props2YAML(path.toFile());
    }

    public String convert() {
        PropertyTree tree = new TreeBuilder(properties).build();
        tree = new ArrayProcessor(tree).apply();
        return new YamlPrinter(tree).invoke();
    }

    private void fillProperties(String source) {
        try {
            properties.load(new StringReader(source));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

