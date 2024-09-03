package me.glicz.airflow.util.serializer;

import me.glicz.airflow.api.plugin.PluginMeta;
import me.glicz.airflow.plugin.AirPluginMeta;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class DependencySerializer implements TypeSerializer<PluginMeta.Dependency> {
    public static final DependencySerializer INSTANCE = new DependencySerializer();

    private DependencySerializer() {
    }

    @Override
    public PluginMeta.Dependency deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return new AirPluginMeta.AirDependency(node);
    }

    @Override
    public void serialize(Type type, PluginMeta.@Nullable Dependency obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException();
    }
}
