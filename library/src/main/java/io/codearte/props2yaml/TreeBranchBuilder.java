package io.codearte.props2yaml;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TreeBranchBuilder {

    private final List<String> key;
    private final Object value;

    public TreeBranchBuilder(List<String> key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Tree build() {
        return key.stream()
                .reduce(new Tree(),
                        (a, b) -> new Tree(b, a.isEmpty() ? value : a),
                        (a, b) -> {
                            throw new IllegalStateException("Parallel processing is not supported");
                        }
                );
    }


}
