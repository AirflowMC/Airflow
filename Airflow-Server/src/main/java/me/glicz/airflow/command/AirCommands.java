package me.glicz.airflow.command;

import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import me.glicz.airflow.api.command.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Stream;

public class AirCommands implements me.glicz.airflow.api.command.Commands {
    private final Commands commands;

    public AirCommands(Commands commands) {
        this.commands = commands;
    }

    @Override
    public void register(@NotNull String namespace, @NotNull LiteralCommandNode<CommandSourceStack> node, @NotNull Collection<String> aliases) {
        Stream.concat(Stream.of(node.getLiteral()), aliases.stream()).forEach(literal -> {
            registerCommand(namespace.toLowerCase() + ":" + literal, node);
            registerCommand(literal, node);
        });
    }

    private void registerCommand(String literal, LiteralCommandNode<CommandSourceStack> node) {
        RootCommandNode<net.minecraft.commands.CommandSourceStack> root = this.commands.getDispatcher().getRoot();

        CommandNode<? extends CommandSourceStack> child = root.getChild(literal);
        if (child instanceof AirCommandNode) {
            return;
        }

        root.getChildren().remove(child);
        root.addChild(new AirCommandNode(literal, node).asVanillaNode());
    }
}
