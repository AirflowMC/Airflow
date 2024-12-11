package me.glicz.airflow.api.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface MessageReceiver extends Audience {
    default void sendMessage(@NotNull String message, @NotNull TagResolver... tagResolvers) {
        sendMessage(MiniMessage.miniMessage().deserialize(message, tagResolvers));
    }

    default void sendRawMessage(@NotNull String message) {
        sendMessage(Component.text(message));
    }
}
