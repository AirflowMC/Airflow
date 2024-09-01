package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;

public class JoinListener implements EventHandler<PlayerJoinEvent> {
    @Override
    public void handle(PlayerJoinEvent e) {
        e.setMessage(null);
        e.getPlayer().sendMessage("Hello! This server runs Airflow!");
    }
}
