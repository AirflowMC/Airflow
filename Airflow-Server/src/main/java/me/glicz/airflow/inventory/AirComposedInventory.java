package me.glicz.airflow.inventory;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import me.glicz.airflow.api.inventory.ComposedInventory;
import me.glicz.airflow.api.inventory.Inventory;
import me.glicz.airflow.api.item.stack.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class AirComposedInventory extends AirInventory implements ComposedInventory {
    private final List<AirInventory> inventories;

    public AirComposedInventory(AirInventory... containers) {
        this.inventories = List.of(containers);
    }

    @Override
    public int getSize() {
        return inventories.stream().mapToInt(AirInventory::getSize).sum();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        Preconditions.checkArgument(slot >= 0 && slot < getSize(), "slot < 0 || slot >= inventory size");

        Iterator<AirInventory> it = inventories.iterator();
        int i = 0;

        while (it.hasNext()) {
            AirInventory inventory = it.next();
            if (i <= slot && i + inventory.getSize() > slot) {
                return inventory.getItem(slot);
            }

            i += inventory.getSize();
        }

        throw new RuntimeException(); // shouldn't happen?
    }

    @Override
    public @NotNull List<ItemStack> getItems() {
        return inventories.stream().flatMap(inventory -> inventory.getItems().stream()).toList();
    }

    @Override
    public int getFirstEmptySlot() {
        for (AirInventory inventory : this.inventories) {
            int firstEmptySlot = inventory.getFirstEmptySlot();
            if (firstEmptySlot != -1) {
                return firstEmptySlot;
            }
        }

        return -1;
    }

    @Override
    protected void setItem0(int slot, Either<ItemStack, net.minecraft.world.item.ItemStack> either) {
        Iterator<AirInventory> it = inventories.iterator();
        int i = 0;

        while (it.hasNext()) {
            AirInventory inventory = it.next();
            if (i <= slot && i + inventory.getSize() > slot) {
                inventory.setItem0(slot - i, either);
                break;
            }

            i += inventory.getSize();
        }
    }

    @Override
    public boolean addItem(@NotNull ItemStack item) {
        return inventories.stream().anyMatch(inventory -> inventory.addItem(item));
    }

    @Override
    public boolean removeItem(@NotNull ItemStack item) {
        //noinspection SimplifyStreamApiCallChains
        return inventories.stream().map(inventory -> inventory.removeItem(item)).anyMatch(Boolean::booleanValue);
    }

    @Override
    public boolean removeItemExact(@NotNull ItemStack item) {
        //noinspection SimplifyStreamApiCallChains
        return inventories.stream().map(inventory -> inventory.removeItemExact(item)).anyMatch(Boolean::booleanValue);
    }

    @Override
    public void clear() {
        inventories.forEach(AirInventory::clear);
    }

    @Override
    public @NotNull Collection<Inventory> getInventories() {
        return inventories.stream().<Inventory>map(Function.identity()).toList();
    }

    @Override
    public @NotNull Inventory getInventoryForSlot(int slot) {
        Preconditions.checkArgument(slot >= 0 && slot < getSize(), "slot < 0 || slot >= inventory size");

        Iterator<AirInventory> it = inventories.iterator();
        int i = 0;

        while (it.hasNext()) {
            AirInventory inventory = it.next();
            if (i <= slot && i + inventory.getSize() > slot) {
                return inventory;
            }

            i += inventory.getSize();
        }

        throw new RuntimeException(); // shouldn't happen?
    }
}
