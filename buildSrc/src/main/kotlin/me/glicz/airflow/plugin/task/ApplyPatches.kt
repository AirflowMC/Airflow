package me.glicz.airflow.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class ApplyPatches : DefaultTask() {
    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @get:Inject
    abstract val exec: ExecOperations

    @TaskAction
    fun run() {
        patchesDir.asFileTree.matching { include("*.patch") }.forEach { patch ->
            exec.exec {
                commandLine("git")
                args(
                    "am",
                    "--3way",
                    "--ignore-whitespace",
                    patch.absolutePath
                )
                workingDir(sources)
            }
        }
    }
}