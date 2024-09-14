package me.glicz.airplane

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property

abstract class AirplaneExtension(objects: ObjectFactory) {
    val minecraftVersion = objects.property<String>()
    val sourcesDir = objects.directoryProperty()
    val patchesDir = objects.directoryProperty()
    val exportFilteredDependencies = objects.mapProperty<Project, List<String>>()
}