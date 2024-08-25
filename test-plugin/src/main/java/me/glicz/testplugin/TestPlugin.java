package me.glicz.testplugin;

import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.NotNull;

public class TestPlugin extends Plugin {
    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        getLogger().info("Welcome to Airflow {}!", context.getServerVersion().getName());
        getLogger().info("Here we are in bootstrap context where server is {}", getServer());
        getLogger().info("Also, note, that plugin uses {} classloader", getClass().getClassLoader());
    }

    @Override
    public void onLoad() {
        getLogger().info("Successfully loaded!");
        getLogger().info("And here, in onLoad, the server is with us! Welcome {}", getServer());
    }

    @Override
    public void onEnable() {
        getLogger().info("Successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully disabled!");
    }
}
