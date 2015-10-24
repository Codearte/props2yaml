package io.codearte.props2yaml;

import java.util.Map;

import static java.lang.String.format;

class AppendBranch {

    public static void appendBranch(Tree rootTree, Tree branchTree) {
        branchTree.entrySet().stream().forEach(entry -> {
            Object result = rootTree.get(entry.getKey());
            if (result == null) {
                rootTree.put(entry.getKey(), entry.getValue());
            } else if (result instanceof Tree) {
                appendBranch((Tree) result, (Tree) entry.getValue());
            } else {
                throwException(entry);
            }
        });
    }

    private static void throwException(Map.Entry<String, Object> entry) {
        throw new IllegalArgumentException(format("Failed for element %s in %s", entry.getValue(), entry.getKey()));
    }

}
