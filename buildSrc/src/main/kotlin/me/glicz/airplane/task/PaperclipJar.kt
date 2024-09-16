package me.glicz.airplane.task

import me.glicz.airplane.paperclip.DownloadContext
import me.glicz.airplane.paperclip.Version
import me.glicz.airplane.paperclip.runtimeLibraries
import me.glicz.airplane.piston.PistonMeta
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.kotlin.dsl.get
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import kotlin.io.path.writeText

abstract class PaperclipJar : DefaultTask() {
    @get:Input
    abstract val mainClass: Property<String>

    @get:Input
    abstract val version: Property<String>

    @get:InputFile
    abstract val vanillaJar: RegularFileProperty

    @get:InputFile
    abstract val vanillaBootstrapJar: RegularFileProperty

    @get:InputFile
    abstract val modifiedJar: RegularFileProperty

    @get:OutputFile
    abstract val outputJar: RegularFileProperty

    protected fun setupPaperclipJar(action: (FileSystem) -> Unit) {
        val output = outputJar.asFile.get().apply { delete() }

        val resolvedVersion = version.get()
        project.configurations["paperclip"].first().copyTo(output)

        FileSystems.newFileSystem(output.toPath()).use { fs ->
            val metaInf = fs.getPath("META-INF")

            metaInf.resolve("main-class").writeText(mainClass.get())

            val mojangServer = PistonMeta.fetchVersionDownloads(PistonMeta.fetchVersion(resolvedVersion)).server
            DownloadContext(resolvedVersion, vanillaBootstrapJar.get().asFile, mojangServer.url).write(project, metaInf)

            Version(resolvedVersion, modifiedJar.get().asFile, project.rootProject.name.lowercase()).write(
                project,
                metaInf
            )

            runtimeLibraries(project).write(project, metaInf)

            action(fs)
        }
    }
}