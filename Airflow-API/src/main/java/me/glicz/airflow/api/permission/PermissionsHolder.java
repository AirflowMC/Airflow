package me.glicz.airflow.api.permission;

import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

/**
 * Represents permissions holder.
 * <p>
 * This class should not be used for custom implementations, instead use {@link PermissionsSource}.
 *
 * @see Permission
 * @see PermissionsSource
 */
public interface PermissionsHolder {
    default boolean hasPermission(@NotNull String permission) {
        try {
            //noinspection PatternValidation
            return hasPermission(Key.key(permission));
        } catch (InvalidKeyException ignored) {
            return false;
        }
    }

    boolean hasPermission(@NotNull Key permission);

    boolean hasPermission(@NotNull Permission permission);

    void addPermissionsSource(@NotNull PermissionSourcePriority priority, @NotNull PermissionsSource source);

    void removePermissionsSource(@NotNull PermissionsSource source);
}
