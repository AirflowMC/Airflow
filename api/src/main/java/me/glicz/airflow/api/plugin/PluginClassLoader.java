package me.glicz.airflow.api.plugin;

public interface PluginClassLoader extends JoinableClassLoader {
    Plugin getPlugin();
}
