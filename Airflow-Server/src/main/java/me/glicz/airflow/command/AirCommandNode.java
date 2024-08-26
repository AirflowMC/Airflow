package me.glicz.airflow.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import me.glicz.airflow.api.command.CommandSourceStack;

public class AirCommandNode extends LiteralCommandNode<CommandSourceStack> {
    public AirCommandNode(String literal, LiteralCommandNode<CommandSourceStack> node) {
        super(literal, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());

        node.getChildren().forEach(this::addChild);
    }

    public LiteralCommandNode<? extends CommandSourceStack> asLiteralNode() {
        return this;
    }

    public LiteralCommandNode<net.minecraft.commands.CommandSourceStack> asVanillaNode() {
        //noinspection unchecked
        return (LiteralCommandNode<net.minecraft.commands.CommandSourceStack>) asLiteralNode();
    }
}
