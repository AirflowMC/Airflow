package me.glicz.airflow.api.item.component;

import net.kyori.adventure.text.Component;

import static me.glicz.airflow.api.item.component.ItemComponentTypeProvider.provider;
import static net.kyori.adventure.key.Key.key;

public final class ItemComponentTypes {
    public static final ItemComponentType.Valued<Integer> MAX_STACK_SIZE = provider().getValued(key("minecraft:max_stack_size"));
    public static final ItemComponentType.Valued<Integer> MAX_DAMAGE = provider().getValued(key("minecraft:max_damage"));
    public static final ItemComponentType.Valued<Integer> DAMAGE = provider().getValued(key("minecraft:damage"));
    public static final ItemComponentType.Valued<Component> ITEM_NAME = provider().getValued(key("minecraft:item_name"));
    public static final ItemComponentType.NonValued HIDE_ADDITIONAL_TOOLTIP = provider().getNonValued(key("minecraft:hide_additional_tooltip"));
    public static final ItemComponentType.NonValued HIDE_TOOLTIP = provider().getNonValued(key("minecraft:hide_tooltip"));
    public static final ItemComponentType.Valued<Integer> REPAIR_COST = provider().getValued(key("minecraft:repair_cost"));
    public static final ItemComponentType.NonValued CREATIVE_SLOT_LOCK = provider().getNonValued(key("minecraft:creative_slot_lock"));
    public static final ItemComponentType.Valued<Integer> ENCHANTMENT_GLINT_OVERRIDE = provider().getValued(key("minecraft:enchantment_glint_override"));
    public static final ItemComponentType.NonValued INTANGIBLE_PROJECTILE = provider().getNonValued(key("minecraft:intangible_projectile"));
    public static final ItemComponentType.NonValued FIRE_RESISTANT = provider().getNonValued(key("minecraft:fire_resistance"));
    public static final ItemComponentType.Valued<Integer> OMINOUS_BOTTLE_AMPLIFIER = provider().getValued(key("minecraft:ominous_bottle_amplifier"));

    private ItemComponentTypes() {
    }
}
