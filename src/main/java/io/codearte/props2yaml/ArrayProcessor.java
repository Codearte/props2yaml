package io.codearte.props2yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayProcessor {

    private final Pattern pattern = Pattern.compile("(.*)\\[(\\d)\\]");

    private TreeMap<String, Object> tree;

    public ArrayProcessor(TreeMap<String, Object> tree) {
        this.tree = tree;
    }

    public TreeMap<String, Object> build() {
        return process(tree);
    }

    private TreeMap<String, Object> process(final TreeMap<String, Object> result) {
        final TreeMap<String, Object> output = new TreeMap<>();
        final Map<String, List> entriesFromList = new HashMap<>();
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

    private List processListElement(List<Object> elements, Object value, int index) {
        ArrayList<Object> result = elements == null ? new ArrayList<>() : new ArrayList<>(elements);
        adjustArray(index, result);
        result.add(index, getValue(value));
        return result;
    }

    private Object getValue(Object value) {
        return value instanceof TreeMap ? process((TreeMap) value) : value;
    }

    private void adjustArray(int index, List<Object> elementList) {
        if (elementList.size() < index) {
            for (int i = elementList.size(); i < index; i++) {
                elementList.add(i, null);
            }
        }
    }
}
