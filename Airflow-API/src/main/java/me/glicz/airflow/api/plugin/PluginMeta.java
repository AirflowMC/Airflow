package me.glicz.airflow.api.plugin;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

public interface PluginMeta {
    @NotNull String getMainClass();

    @NotNull
    @Pattern("[a-zA-Z0-9_]+")
    String getName();

    @NotNull String getVersion();

    String getDescription();

    String @NotNull [] getAuthors();

    String @NotNull [] getContributors();
}
