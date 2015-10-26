package io.codearte.props2yaml;

import java.util.List;

class TreeBranchBuilder {

    private final List<String> key;
    private final Object value;

    public TreeBranchBuilder(List<String> key, Object value) {
        this.key = key;
        this.value = value;
    }

    public PropertyTree build() {
        return key.stream()
                .reduce(new PropertyTree(),
                        (a, b) -> new PropertyTree(b, a.isEmpty() ? value : a),
                        (a, b) -> {
                            throw new IllegalStateException("Parallel processing is not supported");
                        }
                );
    }


}
