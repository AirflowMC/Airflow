package me.glicz.airplane.paperclip

import me.glicz.airplane.util.sha256
import me.glicz.airplane.util.stringHash
import org.gradle.api.Project
import java.io.File
import java.nio.file.Path
import kotlin.io.path.writeText

data class Version(val hash: ByteArray, val id: String, val path: String) : PaperclipAsset {
    constructor(version: String, file: File, name: String) : this(file.sha256, version, "$version/${name}-$version.jar")

    override fun write(project: Project, metaInf: Path) {
        metaInf.resolve("versions.list").writeText(toString())
    }

    override fun toString(): String {
        return "${hash.stringHash}\t$id\t$path"
    }
}