package me.glicz.airflow.api.plugin;

public interface PluginMeta {
    String getName();

    String getDescription();

    String getVersion();

    String[] getAuthors();

    String[] getContributors();
}
