package me.glicz.airflow.api.event.bus;

import me.glicz.airflow.api.event.Event;
import org.jetbrains.annotations.NotNull;

public interface ServerEventBus {
    <E extends Event> @NotNull E dispatch(@NotNull E event);
}
