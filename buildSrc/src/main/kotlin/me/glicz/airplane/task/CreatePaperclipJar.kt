package me.glicz.airplane.task

import io.sigpipe.jbsdiff.Diff
import me.glicz.airplane.paperclip.Patch
import me.glicz.airplane.paperclip.Patches
import me.glicz.airplane.util.sha256
import org.gradle.api.tasks.TaskAction
import kotlin.io.path.createDirectories
import kotlin.io.path.outputStream

abstract class CreatePaperclipJar : PaperclipJar() {
    @TaskAction
    fun run() {
        val resolvedVersion = version.get()

        setupPaperclipJar { fs ->
            val metaInf = fs.getPath("META-INF")

            val patch = metaInf.resolve("versions/$resolvedVersion/server-$resolvedVersion.jar.patch").apply {
                parent.createDirectories()

                outputStream().use { os ->
                    Diff.diff(vanillaJar.asFile.get().readBytes(), modifiedJar.asFile.get().readBytes(), os)
                }
            }

            Patches(
                Patch(
                    "versions",
                    vanillaJar.asFile.get().sha256,
                    patch.sha256,
                    modifiedJar.asFile.get().sha256,
                    "$resolvedVersion/server-$resolvedVersion.jar",
                    "$resolvedVersion/server-$resolvedVersion.jar.patch",
                    "$resolvedVersion/${project.rootProject.name.lowercase()}-$resolvedVersion.jar",
                )
            ).write(project, metaInf)
        }
    }
}