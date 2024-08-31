package me.glicz.airplane.task

import me.glicz.airplane.util.git
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class CopySources : DefaultTask() {
    @get:InputFile
    abstract val inputJar: RegularFileProperty

    @get:Input
    abstract val minecraftVersion: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Inject
    abstract val files: FileOperations

    @TaskAction
    fun run() {
        val output = outputDir.get().asFile.apply {
            deleteRecursively()
            mkdirs()
        }

        git("init", "-b", "main")

        files.copy {
            from(files.zipTree(inputJar))
            include("**/*.java")
            into(output)
        }

        git("add", ".", silentErr = true)
        git(
            "commit",
            "--no-gpg-sign",
            "--author=\"Mojang Studios <mojang@studios.xyz>\"",
            "-m",
            "Minecraft ${minecraftVersion.get()}"
        )
        git("tag", "--no-sign", "initial")
    }

    private fun git(vararg args: String, silentErr: Boolean = false) {
        git {
            args(*args)

            workingDir(outputDir)
            silent(true)
            silentErr(silentErr)
        }
    }
}