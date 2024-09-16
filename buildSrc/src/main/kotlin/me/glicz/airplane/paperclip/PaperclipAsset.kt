package me.glicz.airplane.paperclip

import org.gradle.api.Project
import java.nio.file.Path

interface PaperclipAsset {
    fun write(project: Project, metaInf: Path)
}