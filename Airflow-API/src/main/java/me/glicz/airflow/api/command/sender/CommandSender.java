package me.glicz.airflow.api.command.sender;

import me.glicz.airflow.api.ServerAware;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends Audience, ServerAware {
    String getName();

    Component getDisplayName();

    default void sendMessage(final @NotNull String message, final @NotNull TagResolver... tagResolvers) {
        sendMessage(MiniMessage.miniMessage().deserialize(message, tagResolvers));
    }

    void dispatch(String command);
}
