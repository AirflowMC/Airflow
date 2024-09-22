package me.glicz.airflow.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import net.minecraft.locale.Language;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AdventureUtils {
    // Copied FORMAT_SPECIFIER and FORMAT_SPECIFIER_PATTERN from java.util.Formatter
    // %[argument_index$][flags][width][.precision][t]conversion
    static final String FORMAT_SPECIFIER
            = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    static final Pattern FORMAT_SPECIFIER_PATTERN = Pattern.compile(FORMAT_SPECIFIER);

    public static final ComponentFlattener FLATTENER = ComponentFlattener.basic().toBuilder()
            .complexMapper(TranslatableComponent.class, (component, consumer) -> {
                if (Language.getInstance().has(component.key())) {
                    //noinspection DataFlowIssue
                    String translated = Language.getInstance().getOrDefault(component.key(), component.fallback());
                    //noinspection ConstantValue
                    if (translated == null) {
                        consumer.accept(Component.text(component.key()));
                        return;
                    }

                    Object[] plainArgs = component.arguments().stream()
                            .map(TranslationArgument::value)
                            .toArray();

                    Matcher matcher = FORMAT_SPECIFIER_PATTERN.matcher(translated);
                    int lastArg = 0, lastIndex = 0;

                    while (matcher.find()) {
                        if (matcher.start() > lastIndex) {
                            consumer.accept(Component.text(translated.substring(lastIndex, matcher.start())));
                        }

                        String index = matcher.group(1);
                        int arg = index != null
                                ? Integer.parseInt(index.substring(0, index.length() - 1))
                                : lastArg++;

                        TranslationArgument argument = component.arguments().get(arg);
                        if (argument.value() instanceof ComponentLike componentLike) {
                            consumer.accept(componentLike.asComponent());
                        } else {
                            consumer.accept(Component.text(matcher.group().formatted(plainArgs)));
                        }

                        lastIndex = matcher.end();
                    }

                    if (lastIndex < translated.length()) {
                        consumer.accept(Component.text(translated.substring(lastIndex)));
                    }
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
