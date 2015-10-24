package io.codearte.props2yaml;

import java.util.Properties;
import java.util.function.Function;

import static io.codearte.props2yaml.AppendBranch.appendBranch;
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
                        Function.<String>identity(),
                        key -> asObject(properties.getProperty(key))))
                .forEach((String key, Object value) -> {
                            appendBranch(root, new TreeBranchBuilder(key, value).build());
                        }
                );
        return root;
    }
}
