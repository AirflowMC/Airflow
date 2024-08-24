package me.glicz.airflow.api.util;

public interface Version {
    String getName();

    int getProtocolVersion();

    boolean isStable();
}
