package io.codearte.p2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class TreeBuilder {

    private final Properties properties;

    public TreeBuilder(Properties props) {
        this.properties = props;
    }

    public TreeMap<String, Object> build() {
        TreeMap<String, Object> rootTree = new TreeMap<>();
        for (final String name : properties.stringPropertyNames()) {
            breakProperty(rootTree, name);
        }
        return rootTree;
    }

    private void breakProperty(TreeMap<String, Object> rootTree, String key) {
        String[] split = key.split("\\.");

        List<String> strings = Arrays.asList(split);
        Collections.reverse(strings);

        final String value = properties.getProperty(key);

        TreeMap<String, Object> leafTree = strings.stream()
                .reduce(new TreeMap<>(),
                        (a, b) -> {
                            TreeMap<String, Object> m = new TreeMap<>();
                            if (!a.isEmpty()) {
                                m.put(b, a);
                            } else {
                                m.put(b, ValueConverter.asObject(value));
                            }
                            return m;
                        },
                        (a, b) -> {
                            if (!b.isEmpty())
                                a.put(b.firstKey(), b);
                            return a;
                        });
        createLeaf(rootTree, leafTree);
    }



    private void createLeaf(TreeMap<String, Object> rootTree, TreeMap<String, Object> leafTree) {
        for (Map.Entry<String, Object> reducedEntries : leafTree.entrySet()) {
            Object result = rootTree.get(reducedEntries.getKey());
            if (result == null) {
                rootTree.put(reducedEntries.getKey(), reducedEntries.getValue());
            } else {
                Object o = rootTree.get(reducedEntries.getKey());
                if (o instanceof TreeMap && result instanceof TreeMap) {
                    createLeaf((TreeMap) o, (TreeMap) reducedEntries.getValue());
                } else {
                    throw new IllegalArgumentException("D");
                }
            }
        }
    }
}
