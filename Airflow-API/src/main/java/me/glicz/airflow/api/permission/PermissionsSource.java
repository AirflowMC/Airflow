package me.glicz.airflow.api.permission;

import net.kyori.adventure.key.Key;
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
    boolean includesPermission(@NotNull Key permission);

    default boolean includesPermission(@NotNull Permission permission) {
        return includesPermission(permission.key());
    }
}
