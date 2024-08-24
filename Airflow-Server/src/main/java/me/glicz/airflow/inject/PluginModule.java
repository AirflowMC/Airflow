package me.glicz.airflow.inject;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.event.bus.EventBus;
import me.glicz.airflow.api.plugin.PluginMeta;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

public class PluginModule extends AbstractModule {
    private final AtomicReference<Server> server;
    private final PluginMeta pluginMeta;
    private final EventBus eventBus;
    private final Logger logger;

    public PluginModule(AtomicReference<Server> server, PluginMeta pluginMeta, EventBus eventBus, Logger logger) {
        this.server = server;
        this.pluginMeta = pluginMeta;
        this.eventBus = eventBus;
        this.logger = logger;
    }

    @Override
    protected void configure() {
        bind(ServerReferenceType.INSTANCE).toInstance(this.server);
        bind(PluginMeta.class).toInstance(this.pluginMeta);
        bind(EventBus.class).toInstance(this.eventBus);
        bind(Logger.class).toInstance(this.logger);
    }

    private static class ServerReferenceType extends TypeLiteral<AtomicReference<Server>> {
        private static final ServerReferenceType INSTANCE = new ServerReferenceType();
    }
}
