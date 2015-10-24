package io.codearte.props2yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ArrayProcessor {

    private final Pattern pattern = Pattern.compile("(.*)\\[(\\d+)\\]");

    private final Tree tree;

    public ArrayProcessor(Tree tree) {
        this.tree = tree;
    }

    public Tree build() {
        return process(tree);
    }

    private Tree process(final Tree result) {
        final Tree output = new Tree();
        final Map<String, List<Object>> entriesFromList = new HashMap<>();
        result.entrySet().stream().forEach((entry) -> {
            Matcher matcher = pattern.matcher(entry.getKey());
            if (matcher.find()) {
                String label = matcher.group(1);
                int index = Integer.parseInt(matcher.group(2));
                entriesFromList.put(label, processListElement(entriesFromList.get(label), entry.getValue(), index));
            } else {
                output.put(entry.getKey(), getValue(entry.getValue()));
            }
        });
        output.putAll(entriesFromList);
        return output;
    }

    private List<Object> processListElement(final List<Object> elements, final Object value, final int index) {
        List<Object> result = elements == null ? new ArrayList<>() : new ArrayList<>(elements);
        adjustArray(index, result);
        result.add(index, getValue(value));
        return result;
    }

    private Object getValue(final Object value) {
        return value instanceof Tree ? process((Tree) value) : value;
    }

    private void adjustArray(final int index, List<Object> elementList) {
        if (elementList.size() < index) {
            for (int i = elementList.size(); i < index; i++) {
                elementList.add(i, null);
            }
        }
    }
}
