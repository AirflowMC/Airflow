package me.glicz.airplane.task

import me.glicz.airplane.util.airplaneBuildDir
import me.glicz.airplane.util.asPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import javax.inject.Inject
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists
import kotlin.io.path.name
import kotlin.io.path.outputStream

@CacheableTask
abstract class RemapServerJar : DefaultTask() {
    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val inputJar: RegularFileProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val mappings: RegularFileProperty

    @get:Input
    abstract val remapperArgs: ListProperty<String>

    @get:CompileClasspath
    abstract val minecraftClasspath: ConfigurableFileCollection

    @get:Classpath
    abstract val codebook: ConfigurableFileCollection

    @get:Classpath
    abstract val constants: ConfigurableFileCollection

    @get:Classpath
    abstract val remapper: ConfigurableFileCollection

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFiles
    abstract val paramMappings: ConfigurableFileCollection

    @get:OutputFile
    abstract val outputJar: RegularFileProperty

    @get:Inject
    abstract val exec: ExecOperations

    @TaskAction
    fun run() {
        val output = outputJar.asPath.apply { deleteIfExists() }

        val logFile = output.resolveSibling("${output.name}.log")
        logFile.outputStream().buffered().use { log ->
            exec.javaexec {
                maxHeapSize = "2G"

                standardOutput = log
                errorOutput = log

                classpath(codebook)

                remapperArgs.get().forEach { arg ->
                    args(arg
                        .replace(Regex("\\{tempDir}")) { project.airplaneBuildDir.resolve(".tmp_codebook").absolutePath }
                        .replace(Regex("\\{remapperFile}")) { remapper.singleFile.absolutePath }
                        .replace(Regex("\\{mappingsFile}")) { mappings.asPath.absolutePathString() }
                        .replace(Regex("\\{paramsFile}")) { paramMappings.singleFile.absolutePath }
                        .replace(Regex("\\{constantsFile}")) { constants.singleFile.absolutePath }
                        .replace(Regex("\\{output}")) { outputJar.get().asFile.absolutePath }
                        .replace(Regex("\\{input}")) { inputJar.get().asFile.absolutePath }
                        .replace(Regex("\\{inputClasspath}")) { minecraftClasspath.files.joinToString(":") { it.absolutePath } }
                        .replace(Regex("\\{reportsDir}")) { project.airplaneBuildDir.absolutePath }
                    )
                }
            }
        }
    }
}