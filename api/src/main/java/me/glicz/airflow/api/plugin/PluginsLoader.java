package me.glicz.airflow.api.plugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;

public interface PluginsLoader {
    static @Nullable Plugin getPlugin(Class<?> clazz) {
        if (clazz.getClassLoader() instanceof PluginClassLoader classLoader) {
            return classLoader.getPlugin();
        }

        return null;
    }

    @Nullable Plugin getPlugin(String name);

    @NotNull Collection<Plugin> getPlugins();

    @NotNull File getPluginsFolder();
}
