package me.glicz.airflow.plugin;

import me.glicz.airflow.api.plugin.PluginMeta;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;

public class AirPluginMeta implements PluginMeta {
    private final String mainClass, name, description, version;
    private final String[] authors, contributors;

    public AirPluginMeta(BufferedReader reader) throws ConfigurateException {
        CommentedConfigurationNode node = YamlConfigurationLoader.builder()
                .source(() -> reader)
                .build()
                .load();

        this.mainClass = node.node("main-class").getString();
        this.name = node.node("name").getString();
        this.description = node.node("description").getString();
        this.version = node.node("version").getString();
        this.authors = node.node("authors").get(String[].class);
        this.contributors = node.node("contributors").get(String[].class);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String[] getAuthors() {
        return this.authors;
    }

    @Override
    public String[] getContributors() {
        return this.contributors;
    }
}
