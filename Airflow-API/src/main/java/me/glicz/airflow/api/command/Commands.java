package me.glicz.airflow.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.glicz.airflow.api.command.sender.CommandSender;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Commands {
    @Contract(value = "_ -> new", pure = true)
    static @NotNull LiteralArgumentBuilder<CommandSourceStack> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    @Contract(value = "_, _ -> new", pure = true)
    static <T> @NotNull RequiredArgumentBuilder<CommandSourceStack, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    @Nullable CommandInfo getCommandInfo(String name);

    @NotNull Collection<CommandInfo> getCommandInfos();

    default void register(@NotNull Plugin plugin, @NotNull LiteralCommandNode<CommandSourceStack> node, @Nullable String description, @NotNull Collection<String> aliases) {
        register(plugin, plugin.namespace(), node, description, aliases);
    }

    void register(@NotNull Plugin plugin, @NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @Nullable String description, @NotNull Collection<String> aliases);

    void register(@NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @Nullable String description, @NotNull Collection<String> aliases);

    default void unregister(@NotNull String name) {
        unregister(name, true);
    }

    void unregister(@NotNull String name, boolean unregisterAliases);

    void unregisterAll(@NotNull Plugin plugin);

    default void dispatchCommand(@NotNull CommandSender sender, @NotNull String command) {
        dispatchCommand(sender.createCommandSourceStack(), command);
    }

    void dispatchCommand(@NotNull CommandSourceStack stack, @NotNull String command);
}
