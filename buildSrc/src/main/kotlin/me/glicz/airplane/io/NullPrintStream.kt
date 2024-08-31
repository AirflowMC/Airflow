package me.glicz.airplane.io

import java.io.OutputStream
import java.io.PrintStream
import java.util.*

class NullPrintStream : PrintStream(NullOutputStream()) {
    override fun write(b: Int) {
    }

    override fun write(buf: ByteArray, off: Int, len: Int) {
    }

    override fun print(b: Boolean) {
    }

    override fun print(c: Char) {
    }

    override fun print(i: Int) {
    }

    override fun print(l: Long) {
    }

    override fun print(f: Float) {
    }

    override fun print(d: Double) {
    }

    override fun print(s: CharArray) {
    }

    override fun print(s: String?) {
    }

    override fun print(obj: Any?) {
    }

    override fun println() {
    }

    override fun println(x: Boolean) {
    }

    override fun println(x: Char) {
    }

    override fun println(x: Int) {
    }

    override fun println(x: Long) {
    }

    override fun println(x: Float) {
    }

    override fun println(x: Double) {
    }

    override fun println(x: CharArray) {
    }

    override fun println(x: String?) {
    }

    override fun println(x: Any?) {
    }

    override fun printf(format: String, vararg args: Any?): PrintStream {
        return this
    }

    override fun printf(l: Locale?, format: String, vararg args: Any?): PrintStream {
        return this
    }

    override fun format(format: String, vararg args: Any?): PrintStream {
        return this
    }

    override fun format(l: Locale?, format: String, vararg args: Any?): PrintStream {
        return this
    }

    override fun append(csq: CharSequence): PrintStream {
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): PrintStream {
        return this
    }

    override fun append(c: Char): PrintStream {
        return this
    }
}

class NullOutputStream : OutputStream() {
    override fun write(b: Int) {
    }
}