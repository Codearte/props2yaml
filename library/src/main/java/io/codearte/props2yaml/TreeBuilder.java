package io.codearte.props2yaml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.codearte.props2yaml.ValueConverter.asObject;
import static java.util.stream.Collectors.toMap;

class TreeBuilder {

    private final Properties properties;
    private final static Pattern pattern = Pattern.compile("[0-9]+");
    private final boolean useNumericKeysAsArrayIndexes;

    public TreeBuilder(Properties properties, boolean useNumericKeysAsArrayIndexes) {
        this.properties = properties;
        this.useNumericKeysAsArrayIndexes = useNumericKeysAsArrayIndexes;
    }

    public TreeBuilder(Properties properties) {
        this(properties,true);
    }

    public PropertyTree build() {
        PropertyTree root = new PropertyTree();
        properties.stringPropertyNames().stream()
                .collect(toMap(
                        this::splitPropertyName,
                        propertyName -> asObject(properties.getProperty(propertyName))))
                .forEach(root::appendBranchFromKeyValue);
        return root;
    }


    private List<String> splitPropertyName(String property) {
        List<String> strings = Arrays.asList(property.split("\\."));
        List<String> result = useNumericKeysAsArrayIndexes ? applyArrayNotation(strings) : strings;
        Collections.reverse(result);
        return result;
    }

    private List<String> applyArrayNotation(List<String> strings) {
        List<String> result = new ArrayList<>();
        for (String element : strings) {
            Matcher matcher = pattern.matcher(element);
            if (matcher.matches()) {
                int index = result.size() - 1;
                result.set(index, result.get(index) + String.format("[%s]", element));
            } else {
                result.add(element);
            }
        }
        return result;
    }

}
