package me.glicz.airflow.item.stack;

import me.glicz.airflow.api.item.ItemType;
import me.glicz.airflow.api.item.component.PatchedItemComponentMap;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.item.component.AirPatchedItemComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.jetbrains.annotations.NotNull;

public class AirItemStack implements ItemStack {
    public final net.minecraft.world.item.ItemStack handle;

    public AirItemStack(net.minecraft.world.item.ItemStack handle) {
        this.handle = handle;
    }

    @Override
    public ItemType getType() {
        return handle.getItem().airItemType;
    }

    @Override
    public @NotNull PatchedItemComponentMap getItemComponentMap() {
        if (this.handle.getComponents() instanceof PatchedDataComponentMap patchedMap) {
            return new AirPatchedItemComponentMap(patchedMap);
        }

        return PatchedItemComponentMap.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return this.handle.isEmpty();
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }
}
