package io.codearte.props2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeBranchBuilder {

    private final String key;
    private final Object value;

    public TreeBranchBuilder(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Tree build() {
        return splitKey(key).stream()
                .reduce(new Tree(),
                        (a, b) -> new Tree(b, a.isEmpty() ? value : a),
                        (a, b) -> {
                            throw new IllegalStateException("Parallel processing is not supported");
                        }
                );
    }

    private List<String> splitKey(String key) {
        String[] split = key.split("\\.");
        List<String> strings = Arrays.asList(split);
        Collections.reverse(strings);
        return strings;
    }
}
