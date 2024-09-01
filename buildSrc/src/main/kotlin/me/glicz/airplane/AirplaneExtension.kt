package me.glicz.airplane

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.property

abstract class AirplaneExtension(objects: ObjectFactory) {
    val minecraftVersion = objects.property<String>()
    val sourcesDir = objects.directoryProperty()
    val patchesDir = objects.directoryProperty()
}