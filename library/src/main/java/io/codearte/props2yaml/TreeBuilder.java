package io.codearte.props2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static io.codearte.props2yaml.ValueConverter.asObject;
import static java.util.stream.Collectors.toMap;

class TreeBuilder {

    private final Properties properties;

    public TreeBuilder(Properties properties) {
        this.properties = properties;
    }

    public Tree build() {
        Tree root = new Tree();
        properties.stringPropertyNames().stream()
                .collect(toMap(
                        this::splitPropertyName,
                        propertyName -> asObject(properties.getProperty(propertyName))))
                .forEach(root::appendBranchFromKeyValue);
        return root;
    }

    private List<String> splitPropertyName(String property) {
        List<String> strings = Arrays.asList(property.split("\\."));
        Collections.reverse(strings);
        return strings;
    }
}
