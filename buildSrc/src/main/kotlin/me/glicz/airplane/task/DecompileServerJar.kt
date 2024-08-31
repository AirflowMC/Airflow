package me.glicz.airplane.task

import me.glicz.airplane.util.asPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import javax.inject.Inject
import kotlin.io.path.*

@CacheableTask
abstract class DecompileServerJar : DefaultTask() {
    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val inputJar: RegularFileProperty

    @get:CompileClasspath
    abstract val minecraftClasspath: ConfigurableFileCollection

    @get:Classpath
    abstract val decompiler: ConfigurableFileCollection

    @get:Input
    abstract val decompilerArgs: ListProperty<String>

    @get:OutputFile
    abstract val outputJar: RegularFileProperty

    @get:Inject
    abstract val exec: ExecOperations

    @TaskAction
    fun run() {
        val output = outputJar.asPath.apply { deleteIfExists() }

        val cfg = output.resolveSibling("${output.name}.cfg").apply { deleteIfExists() }
        cfg.writeText(buildString {
            for (file in minecraftClasspath.files) {
                append("--add-external=")
                append(file.toPath().absolutePathString())
                append(System.lineSeparator())
            }
        })

        val logs = output.resolveSibling("${output.name}.log")

        logs.outputStream().buffered().use { log ->
            exec.javaexec {
                mainClass.set("org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler")
                maxHeapSize = "3G"

                standardOutput = log
                errorOutput = log

                classpath(decompiler)
                args(decompilerArgs.get())
                args("-cfg", cfg.absolutePathString())

                args(inputJar.asPath.absolutePathString())
                args(outputJar.asPath.absolutePathString())
            }
        }
    }
}