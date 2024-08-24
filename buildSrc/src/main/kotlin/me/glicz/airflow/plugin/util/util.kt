package me.glicz.airflow.plugin.util

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
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

fun Git(file: File): Git = Git(FileRepositoryBuilder.create(file))