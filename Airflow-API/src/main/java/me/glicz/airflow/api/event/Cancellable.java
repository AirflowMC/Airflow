package me.glicz.airflow.api.event;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
