package me.glicz.airflow.api.inventory.menu;

import static me.glicz.airflow.api.inventory.menu.MenuTypeProvider.provider;
import static net.kyori.adventure.key.Key.key;

import me.glicz.airflow.api.generator.GenerationVersion;
import me.glicz.airflow.api.inventory.menu.view.AnvilView;
import me.glicz.airflow.api.inventory.menu.view.ItemCombinerView;
import me.glicz.airflow.api.inventory.menu.view.LoomView;
import me.glicz.airflow.api.inventory.menu.view.MenuView;

/**
 * @apiNote This class was automatically generated based on internal Minecraft registries. Its content might change in future versions.
 */
@GenerationVersion("1.21.4")
public final class MenuTypes {
    /**
     * {@code minecraft:anvil}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<AnvilView> ANVIL = provider().get(key("minecraft:anvil"));

    /**
     * {@code minecraft:beacon}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> BEACON = provider().get(key("minecraft:beacon"));

    /**
     * {@code minecraft:blast_furnace}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> BLAST_FURNACE = provider().get(key("minecraft:blast_furnace"));

    /**
     * {@code minecraft:brewing_stand}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> BREWING_STAND = provider().get(key("minecraft:brewing_stand"));

    /**
     * {@code minecraft:cartography_table}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> CARTOGRAPHY_TABLE = provider().get(key("minecraft:cartography_table"));

    /**
     * {@code minecraft:crafter_3x3}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> CRAFTER_3x3 = provider().get(key("minecraft:crafter_3x3"));

    /**
     * {@code minecraft:crafting}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<ItemCombinerView> CRAFTING = provider().get(key("minecraft:crafting"));

    /**
     * {@code minecraft:enchantment}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> ENCHANTMENT = provider().get(key("minecraft:enchantment"));

    /**
     * {@code minecraft:furnace}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> FURNACE = provider().get(key("minecraft:furnace"));

    /**
     * {@code minecraft:generic_3x3}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_3x3 = provider().get(key("minecraft:generic_3x3"));

    /**
     * {@code minecraft:generic_9x1}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x1 = provider().get(key("minecraft:generic_9x1"));

    /**
     * {@code minecraft:generic_9x2}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x2 = provider().get(key("minecraft:generic_9x2"));

    /**
     * {@code minecraft:generic_9x3}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x3 = provider().get(key("minecraft:generic_9x3"));

    /**
     * {@code minecraft:generic_9x4}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x4 = provider().get(key("minecraft:generic_9x4"));

    /**
     * {@code minecraft:generic_9x5}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x5 = provider().get(key("minecraft:generic_9x5"));

    /**
     * {@code minecraft:generic_9x6}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> GENERIC_9x6 = provider().get(key("minecraft:generic_9x6"));

    /**
     * {@code minecraft:grindstone}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<ItemCombinerView> GRINDSTONE = provider().get(key("minecraft:grindstone"));

    /**
     * {@code minecraft:hopper}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> HOPPER = provider().get(key("minecraft:hopper"));

    /**
     * {@code minecraft:lectern}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> LECTERN = provider().get(key("minecraft:lectern"));

    /**
     * {@code minecraft:loom}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<LoomView> LOOM = provider().get(key("minecraft:loom"));

    /**
     * {@code minecraft:merchant}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> MERCHANT = provider().get(key("minecraft:merchant"));

    /**
     * {@code minecraft:shulker_box}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> SHULKER_BOX = provider().get(key("minecraft:shulker_box"));

    /**
     * {@code minecraft:smithing}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<ItemCombinerView> SMITHING = provider().get(key("minecraft:smithing"));

    /**
     * {@code minecraft:smoker}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> SMOKER = provider().get(key("minecraft:smoker"));

    /**
     * {@code minecraft:stonecutter}
     * @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
     */
    public static final MenuType<MenuView> STONECUTTER = provider().get(key("minecraft:stonecutter"));

    private MenuTypes() {
    }
}
