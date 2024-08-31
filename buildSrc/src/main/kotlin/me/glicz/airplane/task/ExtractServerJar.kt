package me.glicz.airplane.task

import me.glicz.airplane.util.asPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import java.nio.file.FileSystems
import kotlin.io.path.copyTo
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.readLines

@CacheableTask
abstract class ExtractServerJar : DefaultTask() {
    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val bootstrapJar: RegularFileProperty

    @get:OutputFile
    abstract val serverJar: RegularFileProperty

    @TaskAction
    fun run() {
        val serverJarPath = serverJar.asPath.apply { deleteIfExists() }

        FileSystems.newFileSystem(bootstrapJar.asPath).use { fs ->
            val metaInf = fs.getPath("/META-INF")

            val versions = metaInf.resolve("versions.list")
            check(versions.exists()) { "versions.list not found" }

            val lines = versions.readLines()
            check(lines.isNotEmpty()) { "versions.list is empty" }

            val parts = lines[0].split('\t')
            check(parts.size == 3) { "versions.list is invalid" }

            val serverJar = metaInf.resolve("versions").resolve(parts[2])
            check(serverJar.exists()) { "${parts[2]} not found" }

            serverJar.copyTo(serverJarPath)
        }
    }
}