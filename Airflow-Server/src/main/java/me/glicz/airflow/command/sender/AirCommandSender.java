package me.glicz.airflow.command.sender;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.permission.Permission;
import me.glicz.airflow.api.permission.PermissionSourcePriority;
import me.glicz.airflow.api.permission.PermissionsSource;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AirCommandSender implements CommandSender {
    public final AirServer server;
    public final CommandSource commandSource;
    private final Multimap<PermissionSourcePriority, PermissionsSource> permissionSourceMap = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();

    public AirCommandSender(AirServer server, CommandSource commandSource) {
        this.server = server;
        this.commandSource = commandSource;
    }

    public abstract CommandSourceStack createCommandSourceStack();

    public MinecraftComponentSerializer componentSerializer() {
        return MinecraftComponentSerializer.INSTANCE;
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"}) // TODO Adventure 5.0.0
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        if (type != MessageType.SYSTEM) return;

        this.commandSource.sendSystemMessage(componentSerializer().serialize(message));
    }

    @Override
    public String getName() {
        return createCommandSourceStack().getTextName();
    }

    @Override
    public Component getDisplayName() {
        return componentSerializer().deserialize(createCommandSourceStack().getDisplayName());
    }

    @Override
    public void dispatch(String command) {
        this.server.minecraftServer.getCommands().performPrefixedCommand(createCommandSourceStack(), command);
    }

    @Override
    public boolean hasPermission(@NotNull Key permission) {
        Permission perm = this.server.getPermissions().getPermission(permission);
        if (perm != null) {
            return hasPermission(perm);
        }

        return Boolean.TRUE.equals(hasPermission0(permission));
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return Objects.requireNonNullElseGet(hasPermission0(permission.key()), () -> permission.defaultValue().test(this));
    }

    private Boolean hasPermission0(Key permission) {
        PermissionSourcePriority[] priorities = PermissionSourcePriority.values();
        for (int i = priorities.length - 1; i >= 0; i--) {
            PermissionSourcePriority priority = priorities[i];

            for (PermissionsSource source : this.permissionSourceMap.get(priority)) {
                if (source.includesPermission(permission)) {
                    return source.hasPermission(permission);
                }
            }
        }

        return null;
    }

    @Override
    public void addPermissionsSource(@NotNull PermissionSourcePriority priority, @NotNull PermissionsSource source) {
        this.permissionSourceMap.put(priority, source);
    }

    @Override
    public void removePermissionsSource(@NotNull PermissionsSource source) {
        this.permissionSourceMap.values().remove(source);
    }
}
