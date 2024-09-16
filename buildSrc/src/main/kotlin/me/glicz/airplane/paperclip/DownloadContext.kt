package me.glicz.airplane.paperclip

import me.glicz.airplane.util.sha256
import me.glicz.airplane.util.stringHash
import org.gradle.api.Project
import java.io.File
import java.nio.file.Path
import kotlin.io.path.writeText

data class DownloadContext(val hash: ByteArray, val url: String, val name: String) : PaperclipAsset {
    constructor(version: String, file: File, url: String) : this(file.sha256, url, "mojang-$version.jar")

    override fun write(project: Project, metaInf: Path) {
        metaInf.resolve("download-context").writeText(toString())
    }

    override fun toString(): String {
        return "${hash.stringHash}\t$url\t$name"
    }
}