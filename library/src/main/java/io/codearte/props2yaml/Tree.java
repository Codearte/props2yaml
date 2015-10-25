package io.codearte.props2yaml;

import java.util.TreeMap;

public class Tree extends TreeMap<String, Object> {

    public Tree() {
    }

    public Tree(String key, Object value) {
        put(key, value);
    }

    public void appendBranchCreatedFromKeyValue(String key, Object value) {
        appendBranch(new TreeBranchBuilder(key, value).build());

    }

    private void appendBranch(Tree branchTree) {
        branchTree.forEach((k, v) -> this.merge(k, v,
                (root, branch) -> resolveDuplicates((Tree) root, (Tree) branch)));

    }

    private static Object resolveDuplicates(Tree root, Tree branch) {
        root.appendBranch(branch);
        return root;
    }
}
