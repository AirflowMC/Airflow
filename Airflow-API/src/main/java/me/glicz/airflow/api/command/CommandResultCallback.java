package me.glicz.airflow.api.command;

@FunctionalInterface
public interface CommandResultCallback {
    void onResult(int result);
}
