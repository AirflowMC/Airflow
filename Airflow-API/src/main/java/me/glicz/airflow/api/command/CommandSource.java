package me.glicz.airflow.api.command;

import me.glicz.airflow.api.Server;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface CommandSource extends Audience {
    @NotNull Server getServer();

    default void sendRichMessage(final @NotNull String message, final @NotNull TagResolver... tagResolvers) {
        sendMessage(MiniMessage.miniMessage().deserialize(message, tagResolvers));
    }
}
