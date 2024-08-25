package me.glicz.airflow.api.service;

import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface ServiceProvider<T> {
    @NotNull T getProvider();

    @NotNull Plugin getPlugin();

    @NotNull ServicePriority getPriority();
}
