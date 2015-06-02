package io.codearte.props2yaml;

import java.util.Properties;
import java.util.TreeMap;

class TreeBuilder {

    private final Properties properties;

    public TreeBuilder(Properties properties) {
        this.properties = properties;
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
        new KeyTreeBuilder(rootTree, propertyName, ValueConverter.asObject(value)).build();
    }
}
