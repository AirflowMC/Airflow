package me.glicz.airflow.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface Commands {
    @Contract(value = "_ -> new", pure = true)
    static @NotNull LiteralArgumentBuilder<CommandSourceStack> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    @Contract(value = "_, _ -> new", pure = true)
    static <T> @NotNull RequiredArgumentBuilder<CommandSourceStack, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    default void register(@NotNull Plugin plugin, @NotNull LiteralCommandNode<CommandSourceStack> node) {
        register(plugin, node, List.of());
    }

    default void register(@NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node) {
        register(namespace, node, List.of());
    }

    default void register(@NotNull Plugin plugin, @NotNull LiteralCommandNode<CommandSourceStack> node, @NotNull Collection<String> aliases) {
        register(plugin.getPluginMeta().getName(), node, aliases);
    }

    void register(@NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @NotNull Collection<String> aliases);
}
