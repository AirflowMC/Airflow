package me.glicz.airflow.item;

import me.glicz.airflow.api.block.BlockType;
import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.component.ItemComponentMap;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.item.component.AirItemComponentMap;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AirItemType implements ItemType {
    private final Item handle;

    public AirItemType(Item handle) {
        this.handle = handle;
    }

    @Override
    public @Nullable BlockType getBlockType() {
        if (this.handle instanceof BlockItem blockItem) {
            return blockItem.getBlock().airBlockType;
        }

        return null;
    }

    @Override
    public @NotNull ItemStack newItemStack(int amount) {
        return new net.minecraft.world.item.ItemStack(handle, amount).airItemStack;
    }

    @Override
    public @NotNull Key key() {
        //noinspection PatternValidation
        return Key.key(BuiltInRegistries.ITEM.getKey(this.handle).toString());
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }

    @Override
    public @NotNull ItemComponentMap getItemComponentMap() {
        return new AirItemComponentMap(this.handle.components());
    }
}
