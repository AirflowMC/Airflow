package me.glicz.airplane.task

import me.glicz.airplane.util.git
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class ApplyPatches : DefaultTask() {
    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @TaskAction
    fun run() {
        var patches = 0

        patchesDir.asFileTree
            .matching { include("*.patch") }
            .sortedBy { it.nameWithoutExtension }
            .forEach { patch ->
                git {
                    args("am", "--3way", "--ignore-whitespace", patch.absolutePath)

                    workingDir(sources)
                    silent(true)
                }
                patches++
            }

        println("Successfully applied $patches patches!")
    }
}