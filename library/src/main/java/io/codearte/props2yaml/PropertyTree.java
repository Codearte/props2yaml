package io.codearte.props2yaml;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        branchTree.forEach((key, value) -> {
            Optional<Object> removedValue = repairMixedTypeEntries(key, value);
            merge(key, value, (root, branch) -> resolveDuplicates((PropertyTree) root, (PropertyTree) branch));
            removedValue.ifPresent(object -> appendBranch(new PropertyTree(key, object)));
        });
    }

    private Optional<Object> repairMixedTypeEntries(String key, Object value) {
        if (containsKey(key)) {
            if (get(key) instanceof String) {
                Object removed = get(key);
                replaceEntryWithLongerKey(key, value);
                return Optional.of(removed);
            } else if (value instanceof String) {
                replaceEntryWithLongerKey(key, get(key));
            }
        }
        return Optional.empty();
    }

    private void replaceEntryWithLongerKey(String key, Object value) {
        Map.Entry<String, Object> entry = ((TreeMap<String, Object>) value).entrySet().iterator().next();
        remove(key);
        put(key + "." + entry.getKey(), entry.getValue());
    }

    private static Object resolveDuplicates(PropertyTree root, PropertyTree branch) {
        root.appendBranch(branch);
        return root;
    }
}
