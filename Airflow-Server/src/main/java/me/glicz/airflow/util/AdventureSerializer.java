package me.glicz.airflow.util;

import me.glicz.airflow.AirServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class AdventureSerializer { // TODO Adventure codecs?
    private static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    private final AirServer server;

    public AdventureSerializer(AirServer server) {
        this.server = server;
    }

    public net.minecraft.network.chat.Component toMinecraft(Component component) {
        if (component == null) {
            return null;
        }

        return net.minecraft.network.chat.Component.Serializer.fromJson(
                GSON_SERIALIZER.serializeToTree(component),
                server.minecraftServer.registryAccess()
        );
    }

    public Component fromMinecraft(net.minecraft.network.chat.Component component) {
        if (component == null) {
            return null;
        }

        return GSON_SERIALIZER.deserialize(net.minecraft.network.chat.Component.Serializer.toJson(
                component,
                server.minecraftServer.registryAccess()
        ));
    }
}
