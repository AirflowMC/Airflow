package me.glicz.airplane.paperclip

import me.glicz.airplane.util.asPath
import me.glicz.airplane.util.sha256Digest
import me.glicz.airplane.util.stringHash
import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.kotlin.dsl.get
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeLines

data class Library(private val artifact: ResolvedArtifact) : PaperclipAsset {
    val hash: ByteArray = sha256Digest.digest(artifact.file.readBytes())
    val id: String = artifact.moduleVersion.id.toString()
    val path: String = "${artifact.moduleVersion.id.asPath()}/${artifact.file.name}"

    override fun write(project: Project, metaInf: Path) {
        val libraryPath = metaInf.resolve("libraries").resolve(path)
        libraryPath.parent.createDirectories()

        artifact.file.toPath().copyTo(libraryPath)
    }

    override fun toString(): String {
        return "${hash.stringHash}\t$id\t$path"
    }
}

data class Libraries(val list: List<Library>) : PaperclipAsset {
    override fun write(project: Project, metaInf: Path) {
        metaInf.resolve("libraries.list")
            .apply { createFile() }
            .writeLines(list.map { it.write(project, metaInf); it.toString() })
    }
}

fun runtimeLibraries(project: Project): Libraries {
    return Libraries(project.configurations["runtimeClasspath"].resolvedConfiguration.resolvedArtifacts.map { Library(it) })
}