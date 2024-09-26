package me.glicz.airflow.inventory;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AirInventory implements Inventory {
    @Override
    public void setItems(@NotNull List<ItemStack> items) {
        Preconditions.checkArgument(items.size() <= getSize(), "items size > inventory size");

        for (int i = 0; i < getSize(); i++) {
            setItem0(i, items.size() > i ? Either.left(items.get(i)) : Either.right(net.minecraft.world.item.ItemStack.EMPTY));
        }
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack item) {
        setItem0(slot, Either.left(item));
    }

    protected abstract void setItem0(int slot, Either<ItemStack, net.minecraft.world.item.ItemStack> either);
}
