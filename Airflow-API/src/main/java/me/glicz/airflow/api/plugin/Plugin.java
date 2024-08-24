package me.glicz.airflow.api.plugin;

import com.google.inject.Inject;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.bootstrap.BootstrapContext;
import me.glicz.airflow.api.event.bus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

public abstract class Plugin implements ServerAware {
    @Inject
    private AtomicReference<Server> server;
    @Inject
    private PluginMeta pluginMeta;
    @Inject
    private EventBus eventBus;
    @Inject
    private Logger logger;

    @Override
    public Server getServer() {
        return this.server.get();
    }

    public @NotNull PluginMeta getPluginMeta() {
        return this.pluginMeta;
    }

    public @NotNull EventBus getEventBus() {
        return eventBus;
    }

    public @NotNull Logger getLogger() {
        return this.logger;
    }

    public void bootstrap(@NotNull BootstrapContext ctx) {
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
