package me.glicz.airplane.task.mache

import codechicken.diffpatch.cli.PatchOperation
import codechicken.diffpatch.match.FuzzyLineMatcher
import codechicken.diffpatch.util.LoggingOutputStream
import codechicken.diffpatch.util.PatchMode
import codechicken.diffpatch.util.archiver.ArchiveFormat
import me.glicz.airplane.util.asPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.*
import java.io.PrintStream
import kotlin.io.path.deleteIfExists

@CacheableTask
abstract class ApplyMachePatches : DefaultTask() {
    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val inputJar: RegularFileProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputDirectory
    abstract val patchesDir: DirectoryProperty

    @get:OutputFile
    abstract val outputJar: RegularFileProperty

    @TaskAction
    fun run() {
        val output = outputJar.asPath.apply { deleteIfExists() }

        val printStream = PrintStream(LoggingOutputStream(logger, LogLevel.LIFECYCLE))
        val result = PatchOperation.builder()
            .logTo(printStream)
            .basePath(inputJar.asPath, ArchiveFormat.ZIP)
            .patchesPath(patchesDir.asPath)
            .outputPath(output, ArchiveFormat.ZIP)
            .level(codechicken.diffpatch.util.LogLevel.INFO)
            .mode(PatchMode.OFFSET)
            .minFuzz(FuzzyLineMatcher.DEFAULT_MIN_MATCH_SCORE)
            .build()
            .operate()

        if (result.exit != 0) {
            throw Exception("Failed to apply ${result.summary.failedMatches} mache patches")
        }
    }
}