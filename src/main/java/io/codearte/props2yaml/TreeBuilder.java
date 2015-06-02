package io.codearte.props2yaml;

import java.util.Properties;
import java.util.TreeMap;

public class TreeBuilder {

    private final Properties properties;

    public TreeBuilder(Properties props) {
        this.properties = props;
    }

    public TreeMap<String, Object> build() {
        TreeMap<String, Object> rootTree = new TreeMap<>();
        properties.stringPropertyNames().forEach(
                propertyName -> breakProperty(rootTree, propertyName)
        );
        return rootTree;
    }

    private void breakProperty(TreeMap<String, Object> rootTree, String propertyName) {
        final String value = properties.getProperty(propertyName);
        rootTree.putAll(new KeyTreeBuilder(propertyName, ValueConverter.asObject(value)).build());
    }
}
