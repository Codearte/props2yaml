package io.codearte.props2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class KeyTreeBuilder {

    private final String key;
    private final Object value;

    public KeyTreeBuilder(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public TreeMap<String, Object> build() {
        List<String> strings = splitKey(key);
        TreeMap<String, Object> rootTree = new TreeMap<>();
        TreeMap<String, Object> leafTree = strings.stream()
                .reduce(new TreeMap<>(),
                        (a, b) -> {
                            TreeMap<String, Object> m = new TreeMap<>();
                            if (!a.isEmpty()) {
                                m.put(b, a);
                            } else {
                                m.put(b, value);
                            }
                            return m;
                        },
                        (a, b) -> {
                            if (!b.isEmpty()) {
                                a.put(b.firstKey(), b);
                            }
                            return a;
                        });
        createLeaf(rootTree, leafTree);
        return rootTree;
    }

    private List<String> splitKey(String key) {
        String[] split = key.split("\\.");
        List<String> strings = Arrays.asList(split);
        Collections.reverse(strings);
        return strings;
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
