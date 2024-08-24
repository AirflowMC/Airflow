package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;

@FunctionalInterface
public interface EventHandler<E extends Event> {
    void handle(E event);
}
