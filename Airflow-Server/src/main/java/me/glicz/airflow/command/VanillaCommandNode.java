package me.glicz.airflow.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;

public class VanillaCommandNode extends LiteralCommandNode<CommandSourceStack> {
    public final String description;
    public final String alias;

    public VanillaCommandNode(String literal, LiteralArgumentBuilder<CommandSourceStack> node, String description, String alias) {
        super(literal, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());

        node.getArguments().forEach(this::addChild);

        this.description = description;
        this.alias = alias;
    }
}
