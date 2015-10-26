package io.codearte.props2yaml;

import java.util.List;
import java.util.TreeMap;

public class PropertyTree extends TreeMap<String, Object> {

    public PropertyTree() {
    }

    public PropertyTree(String key, Object value) {
        put(key, value);
    }

    public void appendBranchFromKeyValue(List<String> keyPath, Object value) {
        appendBranch(new TreeBranchBuilder(keyPath, value).build());

    }

    private void appendBranch(PropertyTree branchTree) {
        branchTree.forEach((k, v) -> this.merge(k, v,
                (root, branch) -> resolveDuplicates((PropertyTree) root, (PropertyTree) branch)));
    }

    private static Object resolveDuplicates(PropertyTree root, PropertyTree branch) {
        root.appendBranch(branch);
        return root;
    }
}
