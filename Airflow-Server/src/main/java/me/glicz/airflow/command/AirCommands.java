package me.glicz.airflow.command;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import me.glicz.airflow.api.command.CommandInfo;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.plugin.Plugin;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class AirCommands implements me.glicz.airflow.api.command.Commands {
    private final Multimap<Plugin, String> pluginCommands = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();
    private final Commands commands;

    public AirCommands(Commands commands) {
        this.commands = commands;
    }

    @Override
    public CommandInfo getCommandInfo(String name) {
        CommandDispatcher<? extends CommandSourceStack> dispatcher = commands.getDispatcher();
        return getCommandInfo(dispatcher.getRoot().getChild(name));
    }

    private CommandInfo getCommandInfo(CommandNode<? extends CommandSourceStack> node) {
        if (node == null) {
            return null;
        }

        //noinspection unchecked
        List<String> allUsage = List.of(commands.getDispatcher().getAllUsage(
                ((CommandNode<net.minecraft.commands.CommandSourceStack>) node),
                null, false
        ));

        if (node instanceof AirCommandNode airNode) {
            return new AirCommandInfo(airNode.plugin, airNode.getName(), airNode.description, airNode.aliases, allUsage);
        }

        if (node instanceof VanillaCommandNode vanillaNode) {
            return new AirCommandInfo(null, vanillaNode.getName(), vanillaNode.description, vanillaNode.smartAliases(), allUsage);
        }

        return new AirCommandInfo(null, node.getName(), null, List.of(), allUsage);
    }

    @Override
    public @NotNull Collection<CommandInfo> getCommandInfos() {
        CommandDispatcher<? extends CommandSourceStack> dispatcher = commands.getDispatcher();
        return dispatcher.getRoot().getChildren().stream()
                .map(this::getCommandInfo)
                .toList();
    }

    @Override
    public void register(@NotNull Plugin plugin, @NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @Nullable String description, @NotNull Collection<String> aliases) {
        register0(plugin, namespace, node, description, aliases).forEach(literal -> this.pluginCommands.put(plugin, literal));
    }

    @Override
    public void register(@NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @Nullable String description, @NotNull Collection<String> aliases) {
        register0(null, namespace, node, description, aliases);
    }

    private Collection<String> register0(Plugin plugin, String namespace, LiteralCommandNode<CommandSourceStack> node, String description, Collection<String> aliases) {
        List<String> literals = Stream.concat(Stream.of(node.getLiteral()), aliases.stream())
                .flatMap(literal -> Stream.of(literal, namespace.toLowerCase() + ":" + literal))
                .toList();

        return literals.stream()
                .map(literal -> {
                    List<String> commandAliases = new ArrayList<>(literals);
                    commandAliases.remove(literal);

                    return registerCommand(plugin, literal, node, description, commandAliases) ? literal : null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean registerCommand(Plugin plugin, String literal, LiteralCommandNode<CommandSourceStack> node, String description, List<String> aliases) {
        RootCommandNode<net.minecraft.commands.CommandSourceStack> root = this.commands.getDispatcher().getRoot();

        CommandNode<? extends CommandSourceStack> child = root.getChild(literal);
        if (child instanceof AirCommandNode) {
            return false;
        } else if (child instanceof VanillaCommandNode vanillaNode && root.getChild(vanillaNode.alias) instanceof VanillaCommandNode vanillaNode2) {
            vanillaNode2.alias = null;
        }

        root.getChildren().remove(child);
        root.addChild(new AirCommandNode(plugin, literal, node, description, aliases).asVanillaNode());
        return true;
    }

    @Override
    public void unregister(@NotNull String name, boolean unregisterAliases) {
        if (unregister0(name) instanceof AirCommandNode airNode && unregisterAliases) {
            airNode.aliases.forEach(this::unregister0);
        }
    }

    private CommandNode<? extends CommandSourceStack> unregister0(String name) {
        pluginCommands.values().remove(name);

        RootCommandNode<? extends CommandSourceStack> rootNode = commands.getDispatcher().getRoot();
        CommandNode<? extends CommandSourceStack> child = rootNode.getChild(name);
        rootNode.getChildren().remove(child);

        return child;
    }

    @Override
    public void unregisterAll(@NotNull Plugin plugin) {
        pluginCommands.removeAll(plugin).forEach(this::unregister0);
    }

    @Override
    public void dispatchCommand(@NotNull CommandSourceStack stack, @NotNull String command) {
        commands.performPrefixedCommand((net.minecraft.commands.CommandSourceStack) stack, command);
    }
}
