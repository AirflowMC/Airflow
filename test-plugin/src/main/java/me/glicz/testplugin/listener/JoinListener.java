package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.event.player.PlayerJoinEvent;
import me.glicz.airflow.api.permission.DummyPermissionsSource;
import me.glicz.airflow.api.permission.PermissionSourcePriority;
import net.kyori.adventure.key.Key;

public class JoinListener implements EventHandler<PlayerJoinEvent> {
    @Override
    public void handle(PlayerJoinEvent e) {
        e.setMessage(null);
        e.getPlayer().sendMessage("Hello! This server runs Airflow!");

        DummyPermissionsSource permissionsSource = new DummyPermissionsSource(e.getPlayer());
        permissionsSource.addPermission(Key.key("command"), true);
        permissionsSource.addPermission(Key.key("command/say"), false);
        e.getPlayer().addPermissionsSource(PermissionSourcePriority.NORMAL, permissionsSource);
    }
}
