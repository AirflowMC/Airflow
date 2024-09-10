package me.glicz.airflow.api.plugin;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.event.bus.EventBus;
import me.glicz.airflow.api.plugin.bootstrap.BootstrapContext;
import me.glicz.airflow.api.util.ServerReference;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.key.Namespaced;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;

public abstract class Plugin implements Namespaced, ServerAware {
    private ServerReference serverRef;
    private PluginMeta pluginMeta;
    private File dataFolder;
    private EventBus eventBus;
    private Logger logger;
    private boolean enabled;

    @Override
    public @NotNull @KeyPattern.Namespace String namespace() {
        //noinspection PatternValidation
        return pluginMeta.getName().toLowerCase();
    }

    @Override
    public final Server getServer() {
        return this.serverRef.getServer();
    }

    public final @NotNull PluginMeta getPluginMeta() {
        return this.pluginMeta;
    }

    public @NotNull File getDataFolder() {
        return dataFolder;
    }

    public @NotNull EventBus getEventBus() {
        return eventBus;
    }

    public @NotNull Logger getLogger() {
        return this.logger;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
            getServer().getCommands().unregisterAll(this);
            getServer().getPermissions().unregisterAll(this);
            getServer().getServices().unregisterAll(this);
        }
    }

    public void bootstrap(@NotNull BootstrapContext context) {
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
