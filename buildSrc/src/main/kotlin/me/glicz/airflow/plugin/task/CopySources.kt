package me.glicz.airflow.plugin.task

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.PersonIdent
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
        val output = outputDir.get().asFile.apply { deleteRecursively() }

        val git = Git.init()
            .setDirectory(output)
            .setInitialBranch("main")
            .call()

        files.copy {
            from(files.zipTree(inputJar))
            include("**/*.java")
            into(output)
        }

        git.add()
            .addFilepattern(".")
            .call()

        val ident = PersonIdent("Mojang Studios", "mojang@studios.xyz")

        git.commit()
            .setAuthor(ident)
            .setMessage("Minecraft ${minecraftVersion.get()}")
            .setSign(false)
            .call()

        git.tag()
            .setName("initial")
            .setTagger(ident)
            .setSigned(false)
            .call()
    }
}