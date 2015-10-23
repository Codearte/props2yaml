package io.codearte.props2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

class KeyTreeBuilder {

    private final String key;
    private final Object value;
    private final TreeMap<String, Object> rootTree;

    public KeyTreeBuilder(TreeMap<String, Object> rootTree, String key, Object value) {
        this.rootTree = rootTree;
        this.key = key;
        this.value = value;
    }

    public TreeMap<String, Object> build() {
        List<String> strings = splitKey(key);
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

    private void createLeaf(TreeMap<String, Object> rootTree, TreeMap<String, Object> leafTree) {
        leafTree.entrySet().stream().forEach(entry -> {
            Object result = rootTree.get(entry.getKey());
            if (result == null) {
                rootTree.put(entry.getKey(), entry.getValue());
            } else if (result instanceof TreeMap) {
                createLeaf((TreeMap) result, (TreeMap<String, Object>) entry.getValue());
            } else {
                throw new IllegalArgumentException(String.format("Failed for element %s in %s", entry.getValue(), entry.getKey()));
            }
        });
    }

    private List<String> splitKey(String key) {
        String[] split = key.split("\\.");
        List<String> strings = Arrays.asList(split);
        Collections.reverse(strings);
        return strings;
    }
}
