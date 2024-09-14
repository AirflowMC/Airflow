package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EventHandler<E extends Event> {
    void handle(@NotNull E event);
}
