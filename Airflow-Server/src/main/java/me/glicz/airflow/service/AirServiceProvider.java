package me.glicz.airflow.service;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.service.ServicePriority;
import me.glicz.airflow.api.service.ServiceProvider;
import org.jetbrains.annotations.NotNull;

public class AirServiceProvider<T> implements Comparable<AirServiceProvider<T>>, ServiceProvider<T> {
    private final T provider;
    private final Plugin plugin;
    private final ServicePriority priority;

    public AirServiceProvider(T provider, Plugin plugin, ServicePriority priority) {
        this.provider = provider;
        this.plugin = plugin;
        this.priority = priority;
    }

    @Override
    public @NotNull T getProvider() {
        return this.provider;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public @NotNull ServicePriority getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(@NotNull AirServiceProvider<T> serviceProvider) {
        return priority.compareTo(serviceProvider.priority);
    }
}
