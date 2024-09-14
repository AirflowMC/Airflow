package me.glicz.airflow.scheduler;

import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.plugin.Plugin;

public class AirServerScheduler {
    private final Server server;

    public AirServerScheduler(Server server) {
        this.server = server;
    }

    public void tick() {
        this.server.getPluginsLoader().getPlugins().stream()
                .filter(Plugin::isEnabled)
                .forEach(plugin -> ((AirScheduler) plugin.getScheduler()).tick());
    }
}
