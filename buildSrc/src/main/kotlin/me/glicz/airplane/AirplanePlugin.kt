package me.glicz.airplane

import me.glicz.airplane.task.*
import me.glicz.airplane.task.mache.ApplyMachePatches
import me.glicz.airplane.util.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources

class AirplanePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val airplaneExt = project.extensions.create("airplane", AirplaneExtension::class.java)

        val mache by project.configurations.registering {
            isTransitive = false
        }

        val codebook by project.configurations.registering {
            isTransitive = false
        }

        val paramMappings by project.configurations.registering {
            isTransitive = false
        }

        val constants by project.configurations.registering {
            isTransitive = false
        }

        val remapper by project.configurations.registering {
            isTransitive = false
        }

        val decompiler by project.configurations.registering {
            isTransitive = false
        }

        val minecraftLibrary = project.configurations.register(MINECRAFT_LIBRARY) {
            isTransitive = false
        }

        val paperclip by project.configurations.registering

        project.configurations.named("implementation") {
            extendsFrom(minecraftLibrary.get())
        }

        project.afterEvaluate {
            downloadServerBootstrap(airplaneExt.minecraftVersion.get(), airplaneExt.exportFilteredDependencies.get())
            val macheData = extractMacheArtifact()

            val extractServerJar by project.tasks.registering(ExtractServerJar::class) {
                group = "airplane"

                bootstrapJar.set(project.airplaneDir.resolve(SERVER_BOOSTRAP_JAR))

                serverJar.set(project.airplaneBuildDir.resolve(SERVER_JAR))
            }

            val remapServerJar by project.tasks.registering(RemapServerJar::class) {
                group = "airplane"

                inputJar.set(extractServerJar.flatMap { it.serverJar })
                mappings.set(project.airplaneDir.resolve(SERVER_MAPPINGS))
                remapperArgs.set(macheData.remapperArgs)
                minecraftClasspath.from(minecraftLibrary)
                this.codebook.from(codebook)
                this.remapper.from(remapper)
                this.paramMappings.from(paramMappings)

                outputJar.set(project.airplaneBuildDir.resolve(REMAPPED_JAR))
            }

            val decompileServerJar by project.tasks.registering(DecompileServerJar::class) {
                group = "airplane"

                inputJar.set(remapServerJar.flatMap { it.outputJar })
                this.decompiler.from(decompiler)
                decompilerArgs.set(macheData.decompilerArgs)
                minecraftClasspath.from(minecraftLibrary)

                outputJar.set(project.airplaneBuildDir.resolve(SOURCES_JAR))
            }

            val applyMachePatches by project.tasks.registering(ApplyMachePatches::class) {
                group = "airplane"

                inputJar.set(decompileServerJar.flatMap { it.outputJar })
                patchesDir.set(project.airplaneDir.resolve(PATCHES_DIR))

                outputJar.set(project.airplaneBuildDir.resolve(PATCHED_JAR))
            }

            val copySources by project.tasks.registering(CopySources::class) {
                group = "airplane"

                inputJar.set(applyMachePatches.flatMap { it.outputJar })
                minecraftVersion.set(airplaneExt.minecraftVersion)

                outputDir.set(airplaneExt.sourcesDir)
            }

            val applyPatches by project.tasks.registering(ApplyPatches::class) {
                group = "airplane"

                sources.set(copySources.flatMap { it.outputDir })

                patchesDir.set(airplaneExt.patchesDir)
            }

            val rebuildPatches by project.tasks.registering(RebuildPatches::class) {
                group = "airplane"

                sources.set(airplaneExt.sourcesDir)

                patchesDir.set(airplaneExt.patchesDir)
            }

            val createPaperclipJar by project.tasks.registering(CreatePaperclipJar::class) {
                group = "airplane"

                mainClass.set("net.minecraft.server.Main")
                version.set(airplaneExt.minecraftVersion)
                vanillaJar.set(extractServerJar.flatMap { it.serverJar })
                vanillaBootstrapJar.set(project.airplaneDir.resolve(SERVER_BOOSTRAP_JAR))
                modifiedJar.set(tasks.named<Jar>("jar").flatMap { it.archiveFile })

                outputJar.set(project.layout.buildDirectory.file("libs/${project.rootProject.name}-paperclip-${project.version}.jar"))
            }

            tasks.named("processResources", ProcessResources::class.java) {
                from(zipTree(extractServerJar.flatMap { it.serverJar })) {
                    exclude("**/*.class", "META-INF/**")
                }
            }

            tasks.register("runServer", JavaExec::class) {
                group = "airplane"
                doNotTrackState("Run server")

                mainClass = "net.minecraft.server.Main"
                classpath(
                    project.tasks.named<Jar>("jar"),
                    project.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
                )

                standardInput = System.`in`

                doFirst {
                    workingDir(project.rootDir.resolve("run").apply { mkdirs() })

                    args("--nogui")
                }
            }
        }
    }
}