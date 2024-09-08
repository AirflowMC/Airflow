package me.glicz.airflow.api.permission;

import me.glicz.airflow.api.plugin.Plugin;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Permissions {
    @NotNull Collection<Permission> getPermissions();

    @Nullable Permission getPermission(@NotNull Key permission);

    default void registerPermission(@NotNull Key permission) {
        registerPermission(permission, Permission.DefaultValue.FALSE);
    }

    default void registerPermission(@NotNull Plugin plugin, @NotNull @KeyPattern.Value String permission) {
        registerPermission(plugin, Key.key(plugin, permission));
    }

    default void registerPermission(@NotNull Plugin plugin, @NotNull Key permission) {
        registerPermission(plugin, permission, Permission.DefaultValue.FALSE);
    }

    void registerPermission(@NotNull Key permission, @NotNull Permission.DefaultValue defaultValue);

    default void registerPermission(@NotNull Plugin plugin, @NotNull @KeyPattern.Value String permission, @NotNull Permission.DefaultValue defaultValue) {
        registerPermission(plugin, Key.key(plugin, permission), defaultValue);
    }

    void registerPermission(@NotNull Plugin plugin, @NotNull Key permission, @NotNull Permission.DefaultValue defaultValue);

    void unregisterAll(@NotNull Plugin plugin);
}
