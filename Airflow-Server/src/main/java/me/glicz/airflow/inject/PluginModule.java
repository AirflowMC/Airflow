package me.glicz.airflow.inject;

import com.google.inject.AbstractModule;
import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.event.bus.EventBus;
import me.glicz.airflow.api.plugin.Plugin;
import me.glicz.airflow.api.plugin.PluginMeta;
import me.glicz.airflow.api.util.ServerReference;
import me.glicz.airflow.event.bus.AirEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PluginModule extends AbstractModule {
    final ServerReference serverRef;
    final PluginMeta pluginMeta;
    final File dataFolder;
    final EventBus eventBus;
    final Logger logger;

    public PluginModule(Airflow airflow, Plugin plugin, PluginMeta pluginMeta) {
        this(
                airflow.getServerRef(),
                pluginMeta,
                new File(airflow.pluginLoader.getPluginsFolder(), pluginMeta.getName()),
                new AirEventBus(plugin),
                LoggerFactory.getLogger(pluginMeta.getName())
        );
    }

    public PluginModule(ServerReference serverRef, PluginMeta pluginMeta, File dataFolder, EventBus eventBus, Logger logger) {
        this.serverRef = serverRef;
        this.pluginMeta = pluginMeta;
        this.dataFolder = dataFolder;
        this.eventBus = eventBus;
        this.logger = logger;
    }

    @Override
    protected void configure() {
        bind(ServerReference.class).toInstance(this.serverRef);
        bind(PluginMeta.class).toInstance(this.pluginMeta);
        bind(File.class).toInstance(this.dataFolder);
        bind(EventBus.class).toInstance(this.eventBus);
        bind(Logger.class).toInstance(this.logger);
    }
}
