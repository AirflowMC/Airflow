package me.glicz.airflow.api.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public abstract class FieldBasedGenerator extends Generator {
    private final Class<?> source;

    public FieldBasedGenerator(Class<?> source, String packageName, String className, Class<?> providerType) {
        super(packageName, className, providerType);
        this.source = source;
    }

    @Override
    protected void generateFields(TypeSpec.Builder builder) {
        Arrays.stream(this.source.getDeclaredFields())
                .filter(field -> field.getType() == source)
                .sorted(Comparator.comparing(Field::getName))
                .forEach(field -> {
                    try {
                        builder.addField(createField(field));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    protected abstract FieldSpec createField(Field field) throws IllegalAccessException;
}
