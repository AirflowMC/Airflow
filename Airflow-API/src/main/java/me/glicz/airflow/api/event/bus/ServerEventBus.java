package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;

public interface ServerEventBus {
    <E extends Event> E dispatch(E event);
}
