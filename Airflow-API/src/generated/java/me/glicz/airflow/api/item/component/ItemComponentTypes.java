package me.glicz.airflow.api.item.component;

import static me.glicz.airflow.api.item.component.ItemComponentTypeProvider.provider;
import static net.kyori.adventure.key.Key.key;

import java.lang.Boolean;
import java.lang.Integer;
import me.glicz.airflow.api.generator.GenerationVersion;
import me.glicz.airflow.api.item.lore.ItemLore;
import net.kyori.adventure.text.Component;

/**
 * @apiNote This class was automatically generated based on internal Minecraft registries. Its content might change in future versions.
 */
@GenerationVersion("1.21.1")
public final class ItemComponentTypes {
    /**
     * {@code minecraft:creative_slot_lock}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.NonValued CREATIVE_SLOT_LOCK = provider().getNonValued(key("minecraft:creative_slot_lock"));

    /**
     * {@code minecraft:custom_name}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Component> CUSTOM_NAME = provider().getValued(key("minecraft:custom_name"));

    /**
     * {@code minecraft:damage}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Integer> DAMAGE = provider().getValued(key("minecraft:damage"));

    /**
     * {@code minecraft:enchantment_glint_override}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Boolean> ENCHANTMENT_GLINT_OVERRIDE = provider().getValued(key("minecraft:enchantment_glint_override"));

    /**
     * {@code minecraft:fire_resistant}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.NonValued FIRE_RESISTANT = provider().getNonValued(key("minecraft:fire_resistant"));

    /**
     * {@code minecraft:hide_additional_tooltip}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.NonValued HIDE_ADDITIONAL_TOOLTIP = provider().getNonValued(key("minecraft:hide_additional_tooltip"));

    /**
     * {@code minecraft:hide_tooltip}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.NonValued HIDE_TOOLTIP = provider().getNonValued(key("minecraft:hide_tooltip"));

    /**
     * {@code minecraft:intangible_projectile}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.NonValued INTANGIBLE_PROJECTILE = provider().getNonValued(key("minecraft:intangible_projectile"));

    /**
     * {@code minecraft:item_name}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Component> ITEM_NAME = provider().getValued(key("minecraft:item_name"));

    /**
     * {@code minecraft:lore}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<ItemLore> LORE = provider().getValued(key("minecraft:lore"));

    /**
     * {@code minecraft:max_damage}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Integer> MAX_DAMAGE = provider().getValued(key("minecraft:max_damage"));

    /**
     * {@code minecraft:max_stack_size}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Integer> MAX_STACK_SIZE = provider().getValued(key("minecraft:max_stack_size"));

    /**
     * {@code minecraft:ominous_bottle_amplifier}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Integer> OMINOUS_BOTTLE_AMPLIFIER = provider().getValued(key("minecraft:ominous_bottle_amplifier"));

    /**
     * {@code minecraft:repair_cost}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final ItemComponentType.Valued<Integer> REPAIR_COST = provider().getValued(key("minecraft:repair_cost"));

    private ItemComponentTypes() {
    }
}
