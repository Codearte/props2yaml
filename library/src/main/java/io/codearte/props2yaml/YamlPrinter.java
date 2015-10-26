package io.codearte.props2yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

class YamlPrinter {

    private final Map<String, Object> mainMap;

    public YamlPrinter(PropertyTree mainMap) {
        this.mainMap = mainMap;
    }

    public String invoke() {
        Yaml yaml = new Yaml(dumperOptions());
        return yaml.dump(mainMap);
    }

    private DumperOptions dumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setIndent(4);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        dumperOptions.setLineBreak(DumperOptions.LineBreak.UNIX);
        dumperOptions.setPrettyFlow(true);
        return dumperOptions;
    }
}
