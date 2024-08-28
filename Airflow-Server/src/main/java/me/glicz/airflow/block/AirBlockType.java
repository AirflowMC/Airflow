package me.glicz.airflow.block;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.item.ItemType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class AirBlockType implements BlockType {
    private final Block handle;

    public AirBlockType(Block handle) {
        this.handle = handle;
    }

    @Override
    public ItemType getItemType() {
        return this.handle.asItem().airItemType;
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }
}
