package io.codearte.props2yaml;

import java.util.TreeMap;

public class Tree extends TreeMap<String, Object> {
    public Tree() {
    }

    public Tree(String key, Object value) {
        put(key, value);
    }
}
