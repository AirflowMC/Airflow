package me.glicz.airflow.api.inventory.entity;

import me.glicz.airflow.api.util.Typed;

public enum EquipmentSlot implements Typed<EquipmentSlot.Type> {
    MAIN_HAND(Type.HAND),
    OFF_HAND(Type.HAND),
    FEET(Type.HUMANOID_ARMOR),
    LEGS(Type.HUMANOID_ARMOR),
    CHEST(Type.HUMANOID_ARMOR),
    HEAD(Type.HUMANOID_ARMOR),
    BODY(Type.ANIMAL_ARMOR);

    private final Type type;

    EquipmentSlot(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public enum Type {
        HAND,
        HUMANOID_ARMOR,
        ANIMAL_ARMOR
    }
}
