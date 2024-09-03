package me.glicz.airflow.plugin.loader;

import me.glicz.airflow.api.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class CircularDependencyException extends Exception {
    public CircularDependencyException(List<Plugin> dependencyChain) {
        super("Circular dependency detected: " + dependencyChain.stream()
                .map(plugin -> plugin.getPluginMeta().getName())
                .collect(Collectors.joining(" -> "))
        );
    }
}
