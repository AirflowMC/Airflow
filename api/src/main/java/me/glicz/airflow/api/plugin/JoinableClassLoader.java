package me.glicz.airflow.api.plugin;

public interface JoinableClassLoader {
    Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException;
}
