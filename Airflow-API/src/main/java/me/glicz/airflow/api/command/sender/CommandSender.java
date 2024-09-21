package me.glicz.airflow.api.command.sender;

import me.glicz.airflow.api.ServerAware;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.permission.PermissionsHolder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends Audience, PermissionsHolder, ServerAware {
    @NotNull String getName();

    @NotNull Component getDisplayName();

    boolean isOperator();

    default void sendMessage(@NotNull String message, @NotNull TagResolver... tagResolvers) {
        sendMessage(MiniMessage.miniMessage().deserialize(message, tagResolvers));
    }

    default void sendRawMessage(@NotNull String message) {
        sendMessage(Component.text(message));
    }

    @NotNull CommandSourceStack createCommandSourceStack();
}
