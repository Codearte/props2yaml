package io.codearte.props2yaml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.Properties;

public class Props2YAML {

    private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Properties properties = new Properties();

    Props2YAML(String source) {
        try {
            properties.load(new StringReader(source));
        } catch (IOException e) {
            reportError(e);
        }
    }

    Props2YAML(File file) {
        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
        } catch (IOException e) {
            reportError(e);
        }
    }

    public static Props2YAML fromContent(String content) {
        return new Props2YAML(content);
    }

    public static Props2YAML fromFile(File file) {
        return new Props2YAML(file);
    }

    public static Props2YAML fromFile(Path path) {
        return new Props2YAML(path.toFile());
    }

    public String convert(boolean useNumericKeysAsArrayIndexes) {
        PropertyTree tree = new TreeBuilder(properties,useNumericKeysAsArrayIndexes).build();
        tree = new ArrayProcessor(tree).apply();
        return tree.toYAML();
    }
    public String convert() {
        return convert(true);
    }

    private void reportError(IOException e) {
        LOG.error("Conversion failed", e);
    }

}

