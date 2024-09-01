package me.glicz.airplane.util

import me.glicz.airplane.piston.PistonMeta
import org.gradle.api.Project
import java.nio.file.FileSystems
import kotlin.io.path.exists
import kotlin.io.path.readLines

fun Project.downloadServerBootstrap(version: String) {
    PistonMeta.downloadServerResources(this, version)

    FileSystems.newFileSystem(project.airplaneDir.resolve(SERVER_BOOSTRAP_JAR).toPath()).use { fs ->
        val libraries = fs.getPath("/META-INF/libraries.list")
        check(libraries.exists()) { "libraries.list not found" }

        libraries.readLines().forEach { line ->
            val parts = line.split('\t')
            check(parts.size == 3) { "libraries.list is invalid" }

            project.dependencies.add("minecraftLibrary", parts[1])
        }
    }
}