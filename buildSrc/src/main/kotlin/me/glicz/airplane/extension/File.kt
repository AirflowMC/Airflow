package me.glicz.airplane.extension

import me.glicz.airplane.util.sha1Digest
import java.io.File
import java.util.zip.ZipFile

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

val File.sha1
    get() = sha1Digest.digest(readBytes()).joinToString("") { byte ->
        "%02x".format(byte)
    }