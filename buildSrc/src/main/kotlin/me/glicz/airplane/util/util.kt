package me.glicz.airplane.util

import org.gradle.api.Project
import org.gradle.api.file.FileSystemLocationProperty
import java.io.File
import java.nio.file.Path
import java.security.MessageDigest


val sha1Digest: MessageDigest = MessageDigest.getInstance("SHA-1")

val Project.airflowDir: File
    get() = projectDir.resolve(".gradle/airflow")

val Project.airflowBuildDir: File
    get() = layout.buildDirectory.file("airflow").get().asFile

val FileSystemLocationProperty<*>.asPath: Path
    get() = get().asFile.toPath()