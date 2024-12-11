package me.glicz.airflow.plugin.loader;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta.Dependency;
import me.glicz.airflow.plugin.AirPluginClassLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AirLoadOrderResolver {
    private final AirPluginsLoader loader;
    List<Plugin> loadOrder = null;

    AirLoadOrderResolver(AirPluginsLoader loader) {
        this.loader = loader;
    }

    private static AirPluginClassLoader getClassLoader(Plugin plugin) {
        return (AirPluginClassLoader) plugin.getClass().getClassLoader();
    }

    void resolve() throws UnknownDependencyException, CircularDependencyException {
        Set<Plugin> visited = new HashSet<>();
        List<Plugin> dependencyStack = new ArrayList<>();
        List<Plugin> loadOrder = new ArrayList<>();

        for (Plugin plugin : loader.pluginMap.values()) {
            if (!visited.contains(plugin)) {
                processPlugin(plugin, visited, dependencyStack, loadOrder);
            }
        }

        this.loadOrder = List.copyOf(loadOrder.reversed());
    }

    private void processPlugin(Plugin plugin, Set<Plugin> visited, List<Plugin> dependencyStack, List<Plugin> loadOrder)
            throws UnknownDependencyException, CircularDependencyException {
        visited.add(plugin);
        dependencyStack.addLast(plugin);

        Set<Plugin> loadBefore = new HashSet<>();
        Set<Plugin> loadAfter = new HashSet<>();

        for (Dependency dependency : plugin.getPluginMeta().getDependencies()) {
            Plugin dependencyPlugin = loader.pluginMap.get(dependency.getName());
            if (dependencyPlugin == null) {
                if (dependency.isRequired()) {
                    throw new UnknownDependencyException(plugin, dependency);
                }
                continue;
            }

            if (dependency.shouldJoinClasspath()) {
                getClassLoader(plugin).joinClassLoader(getClassLoader(dependencyPlugin));
            }

            switch (dependency.getLoadOrder()) {
                case AFTER -> loadAfter.add(dependencyPlugin);
                case BEFORE -> loadBefore.add(dependencyPlugin);
            }
        }

        for (Plugin dependency : loadBefore) {
            processDependency(dependency, true, visited, dependencyStack, loadOrder);
        }

        loadOrder.add(plugin);
        dependencyStack.removeLast();

        for (Plugin dependency : loadAfter) {
            processDependency(dependency, false, visited, dependencyStack, loadOrder);
        }
    }

    private void processDependency(Plugin dependency, boolean throwCircular, Set<Plugin> visited, List<Plugin> dependencyStack, List<Plugin> loadOrder)
            throws UnknownDependencyException, CircularDependencyException {
        if (!visited.contains(dependency)) {
            processPlugin(dependency, visited, dependencyStack, loadOrder);
        } else if (throwCircular && dependencyStack.contains(dependency)) {
            List<Plugin> circularDependencies = new ArrayList<>(dependencyStack.subList(dependencyStack.indexOf(dependency), dependencyStack.size()));
            circularDependencies.add(dependency);

            throw new CircularDependencyException(circularDependencies);
        }
    }
}
