package me.glicz.airflow.event.bus;

import me.glicz.airflow.Airflow;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.EventPriority;
import me.glicz.airflow.api.event.bus.ServerEventBus;

import java.util.List;
import java.util.Objects;

public class AirServerEventBus implements ServerEventBus {
    private final Airflow airflow;

    public AirServerEventBus(Airflow airflow) {
        this.airflow = airflow;
    }

    @Override
    public <E extends Event> E dispatch(E event) {
        //noinspection unchecked
        List<EventHandlers<E>> eventHandlers = this.airflow.pluginLoader.getPlugins().stream()
                .map(plugin -> (EventHandlers<E>) ((AirEventBus) plugin.getEventBus()).handlersMap.get(event.getClass()))
                .filter(Objects::nonNull)
                .toList();

        for (EventPriority priority : EventPriority.values()) {
            eventHandlers.forEach(handlers -> handlers.dispatch(priority, event));
        }

        return event;
    }
}
