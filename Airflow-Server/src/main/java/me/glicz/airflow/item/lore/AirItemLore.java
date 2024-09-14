package me.glicz.airflow.item.lore;

import me.glicz.airflow.Handleable;
import me.glicz.airflow.util.MinecraftComponentSerializer;
import net.kyori.adventure.text.Component;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AirItemLore implements Handleable<ItemLore>, me.glicz.airflow.api.item.lore.ItemLore {
    private final ItemLore handle;
    private final List<Component> lines;

    public AirItemLore(ItemLore handle) {
        this.handle = new ItemLore(List.copyOf(handle.lines()));
        this.lines = MinecraftComponentSerializer.INSTANCE.deserialize(this.handle.lines());
    }

    public AirItemLore(List<Component> lines) {
        this.handle = new ItemLore(MinecraftComponentSerializer.INSTANCE.serialize(lines));
        this.lines = List.copyOf(lines);
    }

    @Override
    public ItemLore getHandle() {
        return this.handle;
    }

    @Override
    public @NotNull List<Component> getLines() {
        return this.lines;
    }
}
