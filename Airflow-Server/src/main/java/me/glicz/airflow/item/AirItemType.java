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
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AirItemType implements ItemType {
    private final Item handle;

    public AirItemType(Item handle) {
        this.handle = handle;
    }

    @Override
    public @Nullable BlockType asBlockType() {
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
        @Subst("minecraft:item") String key = BuiltInRegistries.ITEM.getKey(this.handle).toString();
        return Key.key(key);
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }

    @Override
    public @NotNull ItemComponentMap getItemComponentMap() {
        return new AirItemComponentMap(this.handle.components());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirItemType that)) return false;
        return Objects.equals(this.handle, that.handle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.handle);
    }
}
