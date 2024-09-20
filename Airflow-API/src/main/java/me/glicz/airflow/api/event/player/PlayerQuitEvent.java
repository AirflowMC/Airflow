package me.glicz.airflow.api.event.player;

import me.glicz.airflow.api.entity.living.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class PlayerQuitEvent extends PlayerEvent {
    private Component message;

    @ApiStatus.Internal
    public PlayerQuitEvent(Player player, Component message) {
        super(player);
        this.message = message;
    }

    public @Nullable Component getMessage() {
        return message;
    }

    public void setMessage(@Nullable Component message) {
        this.message = message;
    }
}
