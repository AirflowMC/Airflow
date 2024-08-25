package me.glicz.airflow.plugin;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginClassLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class AirPluginClassLoader extends URLClassLoader implements PluginClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    private Plugin plugin = null;

    public AirPluginClassLoader(File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, Airflow.class.getClassLoader());
    }

    @Override
    public @Nullable URL getResource(String name) {
        return findResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        return findResources(name);
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    public void setPlugin(Plugin plugin) {
        if (this.plugin != null) {
            throw new IllegalStateException("Plugin for this classloader is already set");
        }

        this.plugin = plugin;
    }
}
