package me.glicz.airflow.api.permission;

import me.glicz.airflow.api.Server;
import org.jetbrains.annotations.NotNull;

/**
 * Represents permissions source created by plugins.
 * <p>
 * This class is safe (and expected) to be implemented.
 * <br>
 * Airflow API only provides simple {@link DummyPermissionsSource} implementation.
 *
 * @see PermissionsHolder
 * @see DummyPermissionsSource
 */
public interface PermissionsSource extends PermissionsHolder {
    @NotNull PermissionsHolder getHolder();

    @Override
    default Server getServer() {
        return getHolder().getServer();
    }
}
