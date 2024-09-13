package me.glicz.airflow.item.component.adapter;

import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.minecraft.network.chat.Component;

class ComponentAdapter extends ItemComponentAdapter<Component, net.kyori.adventure.text.Component> {
    public ComponentAdapter() {
        super(MinecraftComponentSerializer.INSTANCE::deserialize, MinecraftComponentSerializer.INSTANCE::serialize);
    }
}
