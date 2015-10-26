package io.codearte.props2yaml;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class Props2YAML {

    private final Properties properties = new Properties();

    public Props2YAML(String source) {
        fillProperties(source);
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

