package me.glicz.airflow.api.generator;

import java.lang.annotation.*;

/**
 * Specifies the Minecraft version used to generate the target class.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerationVersion {
    /**
     * @return generation version
     */
    String value();
}
