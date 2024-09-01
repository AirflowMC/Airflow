package me.glicz.airplane.task

import me.glicz.airplane.util.asPath
import me.glicz.airplane.util.git
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteRecursively


abstract class RebuildPatches : DefaultTask() {
    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @OptIn(ExperimentalPathApi::class)
    @TaskAction
    fun run() {
        val patches = patchesDir.asPath.apply { deleteRecursively() }

        git {
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
            silent(true)
        }
        git {
            args("add", ".")

            workingDir(patchesDir)
            silent(true)
            silentErr(true)
        }
    }
}