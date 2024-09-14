package me.glicz.airflow.inventory.entity;

import com.google.common.collect.Streams;
import me.glicz.airflow.api.inventory.entity.EntityEquipment;
import me.glicz.airflow.api.inventory.entity.EquipmentSlot;
import me.glicz.airflow.api.inventory.entity.EquipmentSlotGroup;
import me.glicz.airflow.api.item.stack.ItemStack;
import me.glicz.airflow.entity.living.AirLivingEntity;
import me.glicz.airflow.item.stack.AirItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class AirEntityEquipment implements EntityEquipment {
    private final AirLivingEntity entity;

    public AirEntityEquipment(AirLivingEntity entity) {
        this.entity = entity;
    }

    private net.minecraft.world.entity.EquipmentSlot equipmentSlot(EquipmentSlot slot) {
        return net.minecraft.world.entity.EquipmentSlot.values()[slot.ordinal()];
    }

    @Override
    public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot) {
        return this.entity.getHandle().getItemBySlot(equipmentSlot(slot)).airItemStack;
    }

    @Override
    public @NotNull Collection<ItemStack> getItems(EquipmentSlotGroup group) {
        return switch (group) {
            case ANY ->
                    Streams.stream(this.entity.getHandle().getAllSlots()).<ItemStack>map(itemStack -> itemStack.airItemStack).toList();
            case MAIN_HAND, OFF_HAND, FEET, LEGS, CHEST, HEAD, BODY ->
                    List.of(getItem(EquipmentSlot.valueOf(group.name())));
            case HAND ->
                    Streams.stream(this.entity.getHandle().getHandSlots()).<ItemStack>map(itemStack -> itemStack.airItemStack).toList();
            case ARMOR ->
                    Streams.stream(this.entity.getHandle().getArmorSlots()).<ItemStack>map(itemStack -> itemStack.airItemStack).toList();
        };
    }

    @Override
    public void setItem(@NotNull EquipmentSlot slot, @NotNull ItemStack itemStack) {
        this.entity.getHandle().setItemSlot(equipmentSlot(slot), ((AirItemStack) itemStack).handle);
    }
}
