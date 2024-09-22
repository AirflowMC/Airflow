package me.glicz.airplane.task

import me.glicz.airplane.util.asPath
import me.glicz.airplane.util.git
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import kotlin.io.path.*


abstract class RebuildPatches : DefaultTask() {
    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @OptIn(ExperimentalPathApi::class)
    @TaskAction
    fun run() {
        val patches = patchesDir.asPath

        sources.asPath.resolve(".git/rebase-apply").apply {
            if (!exists()) {
                patches.deleteRecursively()
                return@apply
            }

            val last = resolve("last").readText().trim().toInt()
            val next = resolve("next").run {
                if (exists()) readText().trim().toInt() else null
            }

            git {
                args("am", "--continue")

                workingDir(sources)
                silent(true)
            }

            if (next == null) return@apply

            val orderedFiles = patches.useDirectoryEntries("*.patch") { it.toMutableList() }.apply { sort() }
            for (i in 1..last) {
                if (i < next) {
                    orderedFiles[i].deleteIfExists()
                }
            }
        }

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