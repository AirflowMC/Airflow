package me.glicz.airflow.api.generator;

import com.squareup.javapoet.*;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Registry;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public abstract class Generator {
    public static final String ENTRY_DOC = """
            {@code $L}
            @apiNote This field was automatically generated based on internal Minecraft registries. It might be removed in future versions.
            """;

    private final Registry<?> registry;
    private final String packageName;
    private final String className;
    private final Class<?> entryType;
    private final Class<?> providerType;

    public Generator(Registry<?> registry, String packageName, String className, Class<?> entryType, Class<?> providerType) {
        this.registry = registry;
        this.packageName = packageName;
        this.className = className;
        this.entryType = entryType;
        this.providerType = providerType;
    }

    public void run(String version, File sourceFolder) throws IOException {
        TypeSpec.Builder itemTypes = TypeSpec.classBuilder(this.className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(GenerationVersion.class)
                        .addMember("value", "$S", version)
                        .build()
                )
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .build()
                );

        this.registry.keySet().stream()
                .sorted()
                .forEach(key -> itemTypes.addField(FieldSpec
                        .builder(this.entryType, key.getPath().toUpperCase(Locale.ENGLISH))
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addJavadoc(ENTRY_DOC, key)
                        .initializer("provider().get(key($S))", key)
                        .build()
                ));

        JavaFile javaFile = JavaFile.builder(this.packageName, itemTypes.build())
                .addStaticImport(this.providerType, "provider")
                .addStaticImport(Key.class, "key")
                .build();

        javaFile.writeToFile(sourceFolder);
    }
}
