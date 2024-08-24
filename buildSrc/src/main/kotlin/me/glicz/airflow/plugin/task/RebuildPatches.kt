package me.glicz.airflow.plugin.task

import me.glicz.airflow.plugin.util.asPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteRecursively


abstract class RebuildPatches : DefaultTask() {
    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @get:Inject
    abstract val exec: ExecOperations

    @OptIn(ExperimentalPathApi::class)
    @TaskAction
    fun run() {
        val patches = patchesDir.asPath.apply { deleteRecursively() }

        exec.exec {
            commandLine("git")
            args(
                "format-patch",
                "--zero-commit",
                "--full-index",
                "--no-signature",
                "--no-stat",
                "-N",
                "-o",
                patches.absolutePathString(),
                "initial...HEAD"
            )
            workingDir(sources)
        }
    }
}