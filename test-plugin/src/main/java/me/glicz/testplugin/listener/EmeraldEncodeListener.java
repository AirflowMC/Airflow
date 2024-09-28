package me.glicz.testplugin.listener;

import me.glicz.airflow.api.event.bus.EventHandler;
import me.glicz.airflow.api.event.packet.ItemStackEncodeEvent;
import me.glicz.airflow.api.item.ItemTypes;
import me.glicz.airflow.api.item.component.ItemComponentTypes;
import me.glicz.airflow.api.item.lore.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public class EmeraldEncodeListener implements EventHandler<ItemStackEncodeEvent> {
    @Override
    public void handle(@NotNull ItemStackEncodeEvent e) {
        if (e.getItemStack().getType() != ItemTypes.EMERALD) return;

        e.getItemStack().getItemComponentMap().set(ItemComponentTypes.LORE, ItemLore.itemLore(
                Component.text("This is ItemStackEncodeEvent showcase!", NamedTextColor.AQUA)
                        .decoration(TextDecoration.ITALIC, false),
                Component.text("All Emeralds come with this lore, however it's only visible client-side!", NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false)
        ));
    }
}
