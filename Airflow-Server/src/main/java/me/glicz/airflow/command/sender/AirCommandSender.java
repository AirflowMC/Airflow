package me.glicz.airflow.command.sender;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import me.glicz.airflow.AirServer;
import me.glicz.airflow.api.Server;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.permission.AbstractPermissionsHolder;
import me.glicz.airflow.api.permission.Permission;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AirCommandSender extends AbstractPermissionsHolder implements CommandSender {
    public final AirServer server;
    public final CommandSource commandSource;

    public AirCommandSender(AirServer server, CommandSource commandSource) {
        super(Multimaps.synchronizedMultimap(
                MultimapBuilder.hashKeys().arrayListValues().build()
        ));
        this.server = server;
        this.commandSource = commandSource;
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        if (type != MessageType.SYSTEM) return;

        commandSource.sendSystemMessage(server.componentSerializer().serialize(message));
    }

    @Override
    public boolean hasPermission(@NotNull Key permission) {
        Permission perm = server.getPermissions().getPermission(permission);
        if (perm != null) {
            return hasPermission(perm);
        }

        return Boolean.TRUE.equals(hasPermission0(permission));
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return Objects.requireNonNullElseGet(hasPermission0(permission.key()), () -> permission.getDefaultValue().test(this));
    }
}
