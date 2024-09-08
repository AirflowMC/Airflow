package me.glicz.airflow.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.service.ServicePriority;
import me.glicz.airflow.api.service.ServiceProvider;
import me.glicz.airflow.api.service.Services;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public class AirServices implements Services {
    private final Multimap<Class<?>, ServiceProvider<?>> serviceProviderMap = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();

    @Override
    public <T> @NotNull ServiceProvider<T> register(@NotNull Class<T> service, @NotNull T provider, @NotNull Plugin plugin, @NotNull ServicePriority priority) {
        AirServiceProvider<T> serviceProvider = new AirServiceProvider<>(provider, plugin, priority);
        this.serviceProviderMap.put(service, serviceProvider);
        return serviceProvider;
    }

    @Override
    public void unregisterAll(@NotNull Plugin plugin) {
        this.serviceProviderMap.values().removeIf(serviceProvider -> serviceProvider.getPlugin() == plugin);
    }

    @Override
    public <T> Optional<ServiceProvider<T>> get(@NotNull Class<T> service) {
        //noinspection unchecked
        return this.serviceProviderMap.get(service).stream()
                .sorted()
                .findFirst()
                .map(serviceProvider -> (ServiceProvider<T>) serviceProvider);
    }

    @Override
    public <T> @NotNull Collection<ServiceProvider<T>> getAll(@NotNull Class<T> service) {
        //noinspection unchecked
        return this.serviceProviderMap.get(service).stream()
                .map(serviceProvider -> (ServiceProvider<T>) serviceProvider)
                .toList();
    }

    @Override
    public @NotNull Collection<ServiceProvider<?>> getAll(@NotNull Plugin plugin) {
        return this.serviceProviderMap.values().stream()
                .filter(serviceProvider -> serviceProvider.getPlugin() == plugin)
                .toList();
    }
}
