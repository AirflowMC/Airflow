package me.glicz.airflow.util;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.chat.ComponentSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class MinecraftComponentSerializer implements ComponentSerializer<Component, Component, net.minecraft.network.chat.Component> {
    public static final MinecraftComponentSerializer INSTANCE = new MinecraftComponentSerializer();
    private static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    private final Supplier<HolderLookup.Provider> provider;

    private MinecraftComponentSerializer() {
        this(Suppliers.ofInstance(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY)));
    }

    public MinecraftComponentSerializer(Supplier<HolderLookup.Provider> provider) {
        this.provider = provider;
    }

    @Override
    public @NotNull Component deserialize(@NotNull net.minecraft.network.chat.Component input) {
        return GSON_SERIALIZER.deserializeFromTree(
                ComponentSerialization.CODEC
                        .encodeStart(provider.get().createSerializationContext(JsonOps.INSTANCE), input)
                        .getOrThrow(JsonParseException::new)
        );
    }

    public @NotNull List<Component> deserialize(@NotNull List<net.minecraft.network.chat.Component> input) {
        return input.stream().map(this::deserialize).toList();
    }

    @Override
    public @NotNull net.minecraft.network.chat.Component serialize(@NotNull Component component) {
        return Serializer.fromJson(
                GSON_SERIALIZER.serializeToTree(component), this.provider.get()
        );
    }

    public @NotNull List<net.minecraft.network.chat.Component> serialize(@NotNull List<Component> input) {
        return input.stream().map(this::serialize).toList();
    }
}
