package me.glicz.airplane.paperclip

import me.glicz.airplane.util.stringHash
import org.gradle.api.Project
import java.nio.file.Path
import kotlin.io.path.writeLines

data class Patch(
    val location: String,
    val originalHash: ByteArray,
    val patchHash: ByteArray,
    val outputHash: ByteArray,
    val originalPath: String,
    val patchPath: String,
    val outputPath: String
) {
    override fun toString(): String {
        return "$location\t${originalHash.stringHash}\t${patchHash.stringHash}\t${outputHash.stringHash}\t$originalPath\t$patchPath\t$outputPath"
    }
}

data class Patches(val list: List<Patch>) : PaperclipAsset {
    constructor(patch: Patch) : this(listOf(patch))

    override fun write(project: Project, metaInf: Path) {
        metaInf.resolve("patches.list").writeLines(list.map { it.toString() })
    }
}