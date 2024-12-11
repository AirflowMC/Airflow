package me.glicz.airflow.adventure.provider;

import me.glicz.airflow.util.AdventureUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class AirPlainTextComponentSerializerProvider implements PlainTextComponentSerializer.Provider {
    @Override
    public @NotNull PlainTextComponentSerializer plainTextSimple() {
        return PlainTextComponentSerializer.builder().flattener(AdventureUtils.FLATTENER).build();
    }

    @Override
    public @NotNull Consumer<PlainTextComponentSerializer.Builder> plainText() {
        return builder -> builder.flattener(AdventureUtils.FLATTENER);
    }
}
