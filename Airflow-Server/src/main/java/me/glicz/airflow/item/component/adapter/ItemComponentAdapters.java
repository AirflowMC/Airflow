package me.glicz.airflow.item.component.adapter;

import me.glicz.airflow.Handleable;
import me.glicz.airflow.api.item.component.ItemComponentType;
import me.glicz.airflow.api.util.LazyReference;
import me.glicz.airflow.item.component.AirItemComponentType;
import me.glicz.airflow.item.lore.AirItemLore;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ItemComponentAdapters {
    private static final ItemComponentAdapter<?, ?> IDENTITY_ADAPTER = new ItemComponentAdapter<>(Function.identity(), Function.identity());
    private static final Map<DataComponentType<?>, ItemComponentAdapter<?, ?>> ADAPTER_MAP = new HashMap<>();

    public static void bootstrap() {
        registerIdentity(DataComponents.MAX_STACK_SIZE);
        registerIdentity(DataComponents.MAX_DAMAGE);
        registerIdentity(DataComponents.DAMAGE);
        registerAdapter(DataComponents.ITEM_NAME, new ComponentAdapter());
        registerHandleable(DataComponents.LORE, AirItemLore::new);
        registerAdapter(DataComponents.CUSTOM_NAME, new ComponentAdapter());
        registerIdentity(DataComponents.REPAIR_COST);
        registerIdentity(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        registerIdentity(DataComponents.OMINOUS_BOTTLE_AMPLIFIER);

        BuiltInRegistries.DATA_COMPONENT_TYPE.forEach(type -> {
            if (ADAPTER_MAP.containsKey(type)) return;

            //noinspection unchecked
            registerAdapter((DataComponentType<Unit>) type, new NonValuedAdapter());
        });
    }

    public static LazyReference<AirItemComponentType> wrap(DataComponentType<?> type) {
        if (type.airItemComponentType() != null) {
            return LazyReference.resolved(type.airItemComponentType());
        }

        return LazyReference.lazy(() -> {
            if (BuiltInRegistries.DATA_COMPONENT_TYPE.keySet().isEmpty()) {
                return null;
            }

            ItemComponentAdapter<?, ?> adapter = Objects.requireNonNull(ADAPTER_MAP.get(type));
            if (adapter instanceof NonValuedAdapter) {
                return new AirItemComponentType.NonValued(type);
            }

            return new AirItemComponentType.Valued<>(type);
        });
    }

    public static <M, A> ItemComponentAdapter<M, A> get(ItemComponentType.Valued<A> type) {
        return get((AirItemComponentType.Valued<A>) type);
    }

    public static <M, A> ItemComponentAdapter<M, A> get(AirItemComponentType.Valued<A> type) {
        //noinspection unchecked
        return (ItemComponentAdapter<M, A>) Objects.requireNonNull(ADAPTER_MAP.get(type.handle));
    }

    private static <M, A> void register(DataComponentType<M> type, Function<M, A> asAir, Function<A, M> asMinecraft) {
        ADAPTER_MAP.put(type, new ItemComponentAdapter<>(asAir, asMinecraft));
    }

    private static void registerIdentity(DataComponentType<?> type) {
        ADAPTER_MAP.put(type, IDENTITY_ADAPTER);
    }

    private static <M, A extends Handleable<M>> void registerHandleable(DataComponentType<M> type, Function<M, A> asAir) {
        register(type, asAir, Handleable::getHandle);
    }

    private static <M> void registerAdapter(DataComponentType<M> type, ItemComponentAdapter<M, ?> adapter) {
        ADAPTER_MAP.put(type, adapter);
    }
}
