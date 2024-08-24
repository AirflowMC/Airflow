package me.glicz.airflow.event.bus;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.api.event.Event;
import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.plugin.Plugin;

public class EventHandlers<E extends Event> {
    private final Multimap<Event.Priority, EventHandler<E>> handlerMap = MultimapBuilder
            .enumKeys(Event.Priority.class)
            .arrayListValues()
            .build();

    public void add(Event.Priority priority, EventHandler<E> handler) {
        this.handlerMap.put(priority, handler);
    }

    public void remove(EventHandler<E> handler) {
        this.handlerMap.values().remove(handler);
    }

    public void dispatch(Plugin plugin, E event) {
        for (Event.Priority priority : handlerMap.keySet()) {
            handlerMap.get(priority).forEach(handler -> {
                try {
                    handler.handle(event);
                } catch (Throwable e) {
                    plugin.getLogger().atError()
                            .setCause(e)
                            .log("Failed to handle {}", event.getClass().getSimpleName());
                }
            });
        }
    }
}
