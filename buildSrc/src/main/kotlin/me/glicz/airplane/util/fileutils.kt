package me.glicz.airplane.util

import java.io.File
import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.readBytes

fun File.unzip(dest: File) {
    ZipFile(this).use { zip ->
        zip.entries().asSequence().forEach { entry ->
            val outputFile = File(dest, entry.name)

            if (entry.isDirectory) {
                outputFile.mkdirs()
            } else {
                outputFile.parentFile?.mkdirs()

                zip.getInputStream(entry).use { input ->
                    outputFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }
}

val File.sha1: ByteArray
    get() = sha1Digest.digest(readBytes())

val File.sha256: ByteArray
    get() = sha256Digest.digest(readBytes())

val Path.sha256: ByteArray
    get() = sha256Digest.digest(readBytes())