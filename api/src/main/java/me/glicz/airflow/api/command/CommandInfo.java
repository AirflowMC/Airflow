package me.glicz.airflow.api.command;

import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface CommandInfo {
    @Nullable Plugin getPlugin();

    @NotNull String getName();

    @Nullable String getDescription();

    @NotNull Collection<String> getAliases();

    @NotNull Collection<String> getUsages();
}
