package me.glicz.airflow.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.property

abstract class AirflowExtension(objects: ObjectFactory) {
    val minecraftVersion = objects.property<String>()
    val mache = objects.property<Any>()
    val sourcesDir = objects.directoryProperty()
    val patchesDir = objects.directoryProperty()
}

fun AirflowExtension.papierMache(version: String): String {
    return "io.papermc:mache:$version@zip"
}