package me.glicz.airflow.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.Translator;
import net.minecraft.locale.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public class ServerTranslator implements Translator {
    @Override
    public @NotNull Key name() {
        return Key.key("vanilla");
    }

    @Override
    public @Nullable MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        if (locale.equals(Locale.US) && Language.getInstance().has(key)) {
            return new MessageFormat(Language.getInstance().getOrDefault(key));
        }

        return null;
    }
}
