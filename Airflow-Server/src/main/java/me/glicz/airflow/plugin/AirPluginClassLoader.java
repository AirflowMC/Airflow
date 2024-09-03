package me.glicz.airflow.plugin;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.plugin.JoinableClassLoader;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginClassLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AirPluginClassLoader extends URLClassLoader implements PluginClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    private final List<JoinableClassLoader> joinedClassLoaders = new ArrayList<>();
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
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException ignored) {
        }

        for (JoinableClassLoader classLoader : this.joinedClassLoaders) {
            try {
                return classLoader.loadClass(name, resolve);
            } catch (ClassNotFoundException ignored) {
            }
        }

        throw new ClassNotFoundException(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("me.glicz.airflow") || name.startsWith("net.minecraft")) {
            throw new ClassNotFoundException(name);
        }

        return super.findClass(name);
    }

    public void joinClassLoader(JoinableClassLoader classLoader) {
        this.joinedClassLoaders.add(classLoader);
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
