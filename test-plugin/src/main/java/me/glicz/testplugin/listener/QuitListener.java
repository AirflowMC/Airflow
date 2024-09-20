package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class QuitListener implements EventHandler<PlayerQuitEvent> {
    @Override
    public void handle(@NotNull PlayerQuitEvent e) {
        e.setMessage(null);
        e.getPlayer().getServer().getServerCommandSender().sendMessage("Bye " + e.getPlayer().getName());
    }
}
