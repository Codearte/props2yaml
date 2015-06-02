package io.codearte.props2yaml;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.TreeMap;

public class Props2YAML {

    private final Properties properties;

    public Props2YAML(String source) {
        properties = createProperties(source);
    }

    public String convert() throws IOException {
        TreeMap<String, Object> mainMap = new TreeBuilder(properties).build();
        return new YamlPrinter(mainMap).invoke();
    }

    private Properties createProperties(String source) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(source));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}

