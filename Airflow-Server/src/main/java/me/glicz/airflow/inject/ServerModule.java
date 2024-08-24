package me.glicz.airflow.inject;

import com.google.inject.AbstractModule;
import me.glicz.airflow.api.Server;

public class ServerModule extends AbstractModule {
    private final Server server;

    public ServerModule(Server server) {
        this.server = server;
    }

    @Override
    protected void configure() {
        bind(Server.class).toInstance(server);
    }
}
