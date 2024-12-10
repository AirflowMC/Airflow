package me.glicz.airflow.inventory;

import com.mojang.datafixers.util.Either;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.item.stack.AirItemStack;
import net.minecraft.world.Container;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AirSimpleInventory extends AirInventory {
    private final Container container;

    public AirSimpleInventory(Container container) {
        this.container = container;
    }

    @Override
    public int getSize() {
        return container.getContainerSize();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return container.getItem(slot).airItemStack;
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return container.getItems().stream().<ItemStack>map(itemStack -> itemStack.airItemStack).toList();
    }

    @Override
    public int getFirstEmptySlot() {
        for (int i = 0; i < getSize(); i++) {
            if (getItem(i).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected void setItem0(int slot, Either<ItemStack, net.minecraft.world.item.ItemStack> either) {
        container.setItem(slot, either.map(itemStack -> ((AirItemStack) itemStack).handle, Function.identity()));
    }

    @Override
    public boolean addItem(@NotNull ItemStack item) {
        int firstEmptySlot = getFirstEmptySlot();
        if (firstEmptySlot != -1) {
            setItem(firstEmptySlot, item);
            return true;
        }

        return useSimilar(item, (slot, otherItem) -> setItem(slot, otherItem.withAmount(otherItem.getAmount() + item.getAmount())));
    }

    private boolean useSimilar(ItemStack item, BiConsumer<Integer, ItemStack> consumer) {
        for (int i = 0; i < getSize(); i++) {
            ItemStack otherItem = getItem(i);

            if (otherItem.isEmpty()) continue;
            if (!otherItem.getType().equals(item.getType())) continue;
            if (!otherItem.getItemComponentMap().equals(item.getItemComponentMap())) continue;

            consumer.accept(i, otherItem);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeItem(@NotNull ItemStack item) {
        if (item.isEmpty()) return false;

        return useSimilar(item, (slot, otherItem) -> {
            if (otherItem.getAmount() < item.getAmount()) return;
            setItem(slot, otherItem.withAmount(otherItem.getAmount() - item.getAmount()));
        });
    }

    @Override
    public boolean removeItemExact(@NotNull ItemStack item) {
        if (item.isEmpty()) return false;

        return useSimilar(item, (slot, otherItem) -> {
            if (otherItem.getAmount() != item.getAmount()) return;
            setItem0(slot, Either.right(net.minecraft.world.item.ItemStack.EMPTY));
        });
    }

    @Override
    public void clear() {
        container.clearContent();
    }
}
