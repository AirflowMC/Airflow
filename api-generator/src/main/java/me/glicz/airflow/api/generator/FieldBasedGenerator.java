package me.glicz.airflow.api.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public abstract class FieldBasedGenerator extends Generator {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Class<?> source;
    private final Class<?> type;

    public FieldBasedGenerator(Class<?> source, String packageName, String className, Class<?> providerType) {
        this(source, source, packageName, className, providerType);
    }

    public FieldBasedGenerator(Class<?> source, Class<?> type, String packageName, String className, Class<?> providerType) {
        super(packageName, className, providerType);
        this.source = source;
        this.type = type;
    }

    @Override
    protected void generateFields(TypeSpec.Builder builder) {
        Arrays.stream(this.source.getDeclaredFields())
                .filter(field -> type.isAssignableFrom(field.getType()))
                .sorted(Comparator.comparing(Field::getName))
                .forEach(field -> {
                    try {
                        builder.addField(createField(field));
                    } catch (Exception e) {
                        logger.atError()
                                .setCause(Main.DEBUG ? e : null)
                                .log("Failed to generate field {}.{}", source.getName(), field.getName());
                    }
                });
    }

    protected abstract FieldSpec createField(Field field) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException;
}
