package me.glicz.airplane.util

import me.glicz.airplane.io.NullPrintStream
import org.gradle.api.file.FileSystemLocationProperty
import java.io.*

class GitBuilder {
    private var _args: Array<out String> = emptyArray()
    val args: Array<out String>
        get() = _args

    private var _workingDir: File? = null
    val workingDir: File?
        get() = _workingDir

    private var _silent: Boolean = false
    val silent: Boolean
        get() = _silent

    private var _silentErr: Boolean = false
    val silentErr: Boolean
        get() = _silentErr

    fun args(vararg args: String) {
        _args = args
    }

    fun workingDir(workingDir: File) {
        _workingDir = workingDir
    }

    fun workingDir(workingDir: FileSystemLocationProperty<*>) {
        workingDir(workingDir.asFile.get())
    }

    fun silent(silent: Boolean) {
        _silent = silent
    }

    fun silentErr(silentErr: Boolean) {
        _silentErr = silentErr
    }
}

fun git(function: GitBuilder.() -> Unit) {
    val builder = GitBuilder()
    function(builder)

    val process = ProcessBuilder("git", *builder.args)
        .directory(builder.workingDir)
        .start()

    redirect(process.inputStream, if (builder.silent) NullPrintStream() else System.out)
    redirect(process.errorStream, if (builder.silentErr) NullPrintStream() else System.err)

    val exitCode = process.waitFor()
    if (exitCode != 0) {
        throw RuntimeException("git process ended with $exitCode exit code.")
    }
}

private fun redirect(`is`: InputStream, out: PrintStream) {
    val thread = Thread {
        BufferedReader(InputStreamReader(`is`)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                out.println(line)
            }
        }
    }
    thread.isDaemon = true
    thread.start()
}
