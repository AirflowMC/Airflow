package me.glicz.airflow.plugin.loader;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta;

public class UnknownDependencyException extends Exception {
    public UnknownDependencyException(Plugin plugin, PluginMeta.Dependency dependency) {
        super(plugin.getPluginMeta().getName() + " requires plugin " + dependency.getName());
    }
}
