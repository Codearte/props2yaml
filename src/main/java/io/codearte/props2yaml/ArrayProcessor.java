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
        return scan(tree);
    }

    private TreeMap<String, Object> scan(final TreeMap<String, Object> result) {
        TreeMap<String, Object> output = new TreeMap<>();
        Map<String, List> entriesFromList = new HashMap<>();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            String key = entry.getKey();
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                String label = matcher.group(1);
                int index = Integer.parseInt(matcher.group(2));
                List<Object> elementList = entriesFromList.get(label);
                if (elementList != null) {
                    adjustArray(index, elementList);
                    elementList.add(index, entry.getValue());
                } else {
                    ArrayList<Object> objects = new ArrayList<>(1000);
                    adjustArray(index, objects);
                    objects.add(index, entry.getValue());
                    entriesFromList.put(label, objects);
                }
            } else {
                if (entry.getValue() instanceof TreeMap) {
                    output.put(entry.getKey(), scan((TreeMap) entry.getValue()));
                } else {
                    output.put(entry.getKey(), entry.getValue());
                }
            }
        }
        output.putAll(entriesFromList);
        return output;
    }

    private void adjustArray(int index, List<Object> elementList) {
        if (elementList.size() < index) {
            for (int i = elementList.size(); i < index; i++) {
                elementList.add(i, null);
            }
        }
    }
}
