package me.glicz.airflow.api.service;

import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface Services {
    <T> @NotNull ServiceProvider<T> register(@NotNull Class<T> service, @NotNull T provider, @NotNull Plugin plugin, @NotNull ServicePriority priority);

    void unregisterAll(@NotNull Plugin plugin);

    <T> Optional<ServiceProvider<T>> get(@NotNull Class<T> service);

    <T> @NotNull Collection<ServiceProvider<T>> getAll(@NotNull Class<T> service);

    @NotNull Collection<ServiceProvider<?>> getAll(@NotNull Plugin plugin);
}
