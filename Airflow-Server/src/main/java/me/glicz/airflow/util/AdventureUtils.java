package me.glicz.airflow.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import net.minecraft.locale.Language;

import java.util.Locale;
import java.util.Objects;

public final class AdventureUtils {
    public static final ComponentFlattener FLATTENER = ComponentFlattener.basic().toBuilder()
            .complexMapper(TranslatableComponent.class, (component, consumer) -> {
                if (Language.getInstance().has(component.key())) {
                    String translated = Language.getInstance()
                            .getOrDefault(component.key(), Objects.requireNonNullElse(component.fallback(), component.key()))
                            .formatted(component.arguments().stream()
                                    .map(TranslationArgument::value)
                                    .toArray()
                            );
                    consumer.accept(Component.text(translated, component.style()));
                    return;
                }

                for (Translator translator : GlobalTranslator.translator().sources()) {
                    if (translator instanceof TranslationRegistry registry && registry.contains(component.key())) {
                        consumer.accept(GlobalTranslator.render(component, Locale.US));
                        return;
                    }
                }
            })
            .build();
    public static final ANSIComponentSerializer ANSI_SERIALIZER = ANSIComponentSerializer.builder().flattener(FLATTENER).build();

    private AdventureUtils() {
    }
}
