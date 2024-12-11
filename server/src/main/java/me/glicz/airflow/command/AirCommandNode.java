package me.glicz.airflow.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import me.glicz.airflow.api.command.CommandSourceStack;
import me.glicz.airflow.api.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AirCommandNode extends LiteralCommandNode<CommandSourceStack> {
    @Nullable
    public final Plugin plugin;
    public final String description;
    public final List<String> aliases;

    public AirCommandNode(@Nullable Plugin plugin, String literal, LiteralCommandNode<CommandSourceStack> node, String description, List<String> aliases) {
        super(literal, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());

        node.getChildren().forEach(this::addChild);

        this.plugin = plugin;
        this.description = description;
        this.aliases = List.copyOf(aliases);
    }

    public LiteralCommandNode<? extends CommandSourceStack> asLiteralNode() {
        return this;
    }

    public LiteralCommandNode<net.minecraft.commands.CommandSourceStack> asVanillaNode() {
        //noinspection unchecked
        return (LiteralCommandNode<net.minecraft.commands.CommandSourceStack>) asLiteralNode();
    }
}
