package me.glicz.airflow.item;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.stack.ItemStack;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class AirItemType implements ItemType {
    private final Item handle;

    public AirItemType(Item handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String translationKey() {
        return this.handle.getDescriptionId();
    }

    @Override
    public ItemStack asItemStack(int amount) {
        return new net.minecraft.world.item.ItemStack(handle, amount).airItemStack;
    }
}
