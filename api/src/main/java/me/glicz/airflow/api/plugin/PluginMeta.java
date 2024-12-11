package me.glicz.airflow.api.plugin;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface PluginMeta {
    @NotNull String getMainClass();

    @NotNull
    @Pattern("[a-zA-Z0-9_]+")
    String getName();

    @NotNull String getVersion();

    @Nullable String getDescription();

    @NotNull Collection<String> getAuthors();

    @NotNull Collection<String> getContributors();

    @NotNull Collection<Dependency> getDependencies();

    interface Dependency {
        @NotNull String getName();

        @NotNull LoadOrder getLoadOrder();

        boolean isRequired();

        boolean shouldJoinClasspath();

        enum LoadOrder {
            AFTER,
            BEFORE,
            OMIT
        }
    }
}
