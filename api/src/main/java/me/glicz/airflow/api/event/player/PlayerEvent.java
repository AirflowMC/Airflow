package me.glicz.airflow.api.event.player;

import me.glicz.airflow.api.entity.living.Player;
import me.glicz.airflow.api.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerEvent extends Event {
    private final Player player;

    public PlayerEvent(@NotNull Player player) {
        this.player = player;
    }

    public @NotNull Player getPlayer() {
        return player;
    }
}
