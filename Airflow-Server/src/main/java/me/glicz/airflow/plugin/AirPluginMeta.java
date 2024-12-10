package me.glicz.airflow.plugin;

import me.glicz.airflow.api.plugin.PluginMeta;
import me.glicz.airflow.util.serializer.DependencySerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class AirPluginMeta implements PluginMeta {
    public static final Pattern PLUGIN_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]+");

    private final String mainClass, name, version, description;
    private final Collection<String> authors, contributors;
    private final Collection<Dependency> dependencies;

    public AirPluginMeta(InputStream is) throws ConfigurateException {
        this(new BufferedReader(new InputStreamReader(is)));
    }

    public AirPluginMeta(BufferedReader reader) throws ConfigurateException {
        CommentedConfigurationNode node = YamlConfigurationLoader.builder()
                .source(() -> reader)
                .defaultOptions(options -> options.serializers(builder -> builder
                        .register(Dependency.class, DependencySerializer.INSTANCE)
                ))
                .build()
                .load();

        this.mainClass = Objects.requireNonNull(node.node("main-class").getString(), "main-class cannot be null");
        this.name = Objects.requireNonNull(node.node("name").getString(), "name cannot be null");
        if (!PLUGIN_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Plugin name must follow [a-zA-Z0-9_]+ pattern! " + this.name);
        }

        this.version = Objects.requireNonNull(node.node("version").getString(), "version cannot be null");
        this.description = node.node("description").getString();
        this.authors = node.node("authors").getList(String.class, List.of());
        this.contributors = node.node("contributors").getList(String.class, List.of());
        this.dependencies = node.node("dependencies").getList(Dependency.class, List.of());
    }

    @Override
    public @NotNull String getMainClass() {
        return mainClass;
    }

    @Override
    public @NotNull @org.intellij.lang.annotations.Pattern("[a-zA-Z0-9_]+") String getName() {
        return name;
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public @NotNull Collection<String> getAuthors() {
        return List.copyOf(authors);
    }

    @Override
    public @NotNull Collection<String> getContributors() {
        return List.copyOf(contributors);
    }

    @Override
    public @NotNull Collection<Dependency> getDependencies() {
        return List.copyOf(dependencies);
    }

    public static class AirDependency implements Dependency {
        private final String name;
        private final LoadOrder loadOrder;
        private final boolean required, joinClasspath;

        public AirDependency(ConfigurationNode node) throws SerializationException {
            this.name = Objects.requireNonNull(node.node("name").getString(), "name cannot be null");
            this.loadOrder = node.node("load").get(LoadOrder.class, LoadOrder.BEFORE);
            this.required = node.node("required").getBoolean(true);
            this.joinClasspath = node.node("join-classpath").getBoolean(true);
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        @Override
        public @NotNull LoadOrder getLoadOrder() {
            return loadOrder;
        }

        @Override
        public boolean isRequired() {
            return required;
        }

        @Override
        public boolean shouldJoinClasspath() {
            return joinClasspath;
        }
    }
}
