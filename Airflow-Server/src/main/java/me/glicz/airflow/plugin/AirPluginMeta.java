package me.glicz.airflow.plugin;

import me.glicz.airflow.api.plugin.PluginMeta;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Pattern;

public class AirPluginMeta implements PluginMeta {
    public static final Pattern PLUGIN_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]+");

    private final String mainClass, name, version, description;
    private final String[] authors, contributors;

    public AirPluginMeta(InputStream is) throws ConfigurateException {
        this(new BufferedReader(new InputStreamReader(is)));
    }

    public AirPluginMeta(BufferedReader reader) throws ConfigurateException {
        CommentedConfigurationNode node = YamlConfigurationLoader.builder()
                .source(() -> reader)
                .build()
                .load();

        this.mainClass = Objects.requireNonNull(node.node("main-class").getString(), "main-class cannot be null");
        this.name = Objects.requireNonNull(node.node("name").getString(), "name cannot be null");
        if (!PLUGIN_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Plugin name must follow [a-zA-Z0-9_]+ pattern! " + this.name);
        }

        this.version = Objects.requireNonNull(node.node("version").getString(), "version cannot be null");
        this.description = node.node("description").getString();
        this.authors = node.node("authors").get(String[].class, new String[0]);
        this.contributors = node.node("contributors").get(String[].class, new String[0]);
    }

    @Override
    public @NotNull String getMainClass() {
        return mainClass;
    }

    @Override
    public @NotNull @org.intellij.lang.annotations.Pattern("[a-zA-Z0-9_]+") String getName() {
        return this.name;
    }

    @Override
    public @NotNull String getVersion() {
        return this.version;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String @NotNull [] getAuthors() {
        return this.authors.clone();
    }

    @Override
    public String @NotNull [] getContributors() {
        return this.contributors.clone();
    }
}
