package me.glicz.airflow.api.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import javax.lang.model.element.Modifier;
import java.util.Comparator;
import java.util.Locale;

public class RegistryBasedGenerator extends Generator {
    private final Registry<?> registry;
    private final Class<?> entryType;

    public RegistryBasedGenerator(Registry<?> registry, String packageName, String className, Class<?> entryType, Class<?> providerType) {
        super(packageName, className, providerType);
        this.registry = registry;
        this.entryType = entryType;
    }

    @Override
    protected void generateFields(TypeSpec.Builder builder) {
        this.registry.keySet().stream()
                .sorted(Comparator.comparing(ResourceLocation::getPath))
                .forEach(key -> builder.addField(FieldSpec
                        .builder(this.entryType, key.getPath().toUpperCase(Locale.ENGLISH))
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .addJavadoc(ENTRY_DOC, key)
                        .initializer("provider().get(key($S))", key)
                        .build()
                ));
    }
}
