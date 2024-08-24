package me.glicz.airflow.api.plugin;

import com.google.inject.Inject;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.event.bus.EventBus;
import org.slf4j.Logger;

public abstract class Plugin {
    @Inject
    private Server server;
    @Inject
    private PluginMeta pluginMeta;
    @Inject
    private EventBus eventBus;
    @Inject
    private Logger logger;

    public Server getServer() {
        return this.server;
    }

    public PluginMeta getPluginMeta() {
        return this.pluginMeta;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
