package io.codearte.props2yaml.maven;

import io.codearte.props2yaml.Props2YAML;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mojo(name = "convert", requiresProject = false)
public class ConvertMojo extends AbstractMojo {

    @Parameter(defaultValue = "${basedir}")
    private File output;

    @Parameter(property = "properties", required = false)
    private String properties;

    @Parameter(property = "useNumericKeysAsArrayIndexes", required = false, defaultValue = "true")
    private boolean useNumericKeysAsArrayIndexes;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (properties == null || properties.isEmpty()) {
            throw new MojoExecutionException("Param 'properties' is required");
        }

        Path propertiesPath = propertiesPath();
        try {
            getLog().info("Properties to convert: " + propertiesPath);
            String content = new String(Files.readAllBytes(propertiesPath));
            String yaml = Props2YAML.fromContent(content).convert(useNumericKeysAsArrayIndexes);
            Path destinationPath = propertiesPath.getParent().resolve(getFileName());
            getLog().info("Write YAML to: " + destinationPath);
            try (BufferedWriter writer = Files.newBufferedWriter(destinationPath)) {
                writer.write(yaml);
            }
        } catch (IOException e) {
            getLog().error("Failed to convert properties", e);
        }
    }

    private Path propertiesPath() {
        File file = new File(properties);
        if (!file.isAbsolute()) {
            file = new File(output, properties);
        }
        return file.toPath();
    }

    private String getFileName() {
        if (properties.contains(".")) {
            return properties.substring(0, properties.lastIndexOf('.')) + ".yml";
        } else {
            return properties + ".yml";
        }
    }

}
