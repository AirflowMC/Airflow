package me.glicz.airflow.plugin

import me.glicz.airflow.plugin.task.*
import me.glicz.airflow.plugin.task.mache.ApplyMachePatches
import me.glicz.airflow.plugin.util.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources

class AirflowPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val airflowExt = project.extensions.create("airflow", AirflowExtension::class.java)

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

        val minecraft by project.configurations.registering {
            isTransitive = false
        }

        project.configurations.named("implementation") {
            extendsFrom(minecraft.get())
        }

        project.afterEvaluate {
            val macheData = downloadMacheArtifact()
            downloadServerBootstrap()

            val extractServerJar by project.tasks.registering(ExtractServerJar::class) {
                group = "airflow"

                bootstrapJar.set(project.airflowDir.resolve(SERVER_BOOSTRAP_JAR))

                serverJar.set(project.airflowBuildDir.resolve(SERVER_JAR))
            }

            val remapServerJar by project.tasks.registering(RemapServerJar::class) {
                group = "airflow"

                inputJar.set(extractServerJar.flatMap { it.serverJar })
                mappings.set(project.airflowDir.resolve(SERVER_MAPPINGS))
                remapperArgs.set(macheData.remapperArgs)
                minecraftClasspath.from(minecraft)
                this.codebook.from(codebook)
                this.remapper.from(remapper)
                this.paramMappings.from(paramMappings)

                outputJar.set(project.airflowBuildDir.resolve(REMAPPED_JAR))
            }

            val decompileServerJar by project.tasks.registering(DecompileServerJar::class) {
                group = "airflow"

                inputJar.set(remapServerJar.flatMap { it.outputJar })
                this.decompiler.from(decompiler)
                decompilerArgs.set(macheData.decompilerArgs)
                minecraftClasspath.from(minecraft)

                outputJar.set(project.airflowBuildDir.resolve(SOURCES_JAR))
            }

            val applyMachePatches by project.tasks.registering(ApplyMachePatches::class) {
                group = "airflow"

                inputJar.set(decompileServerJar.flatMap { it.outputJar })
                patchesDir.set(project.airflowDir.resolve(PATCHES_DIR))

                outputJar.set(project.airflowBuildDir.resolve(PATCHED_JAR))
            }

            val copySources by project.tasks.registering(CopySources::class) {
                group = "airflow"

                inputJar.set(applyMachePatches.flatMap { it.outputJar })
                minecraftVersion.set(airflowExt.minecraftVersion)

                outputDir.set(airflowExt.sourcesDir)
            }

            val applyPatches by project.tasks.registering(ApplyPatches::class) {
                group = "airflow"

                sources.set(copySources.flatMap { it.outputDir })

                patchesDir.set(airflowExt.patchesDir)
            }

            val rebuildPatches by project.tasks.registering(RebuildPatches::class) {
                group = "airflow"

                sources.set(airflowExt.sourcesDir)

                patchesDir.set(airflowExt.patchesDir)
            }

            tasks.named("processResources", ProcessResources::class.java) {
                from(zipTree(extractServerJar.flatMap { it.serverJar })) {
                    exclude("**/*.class", "META-INF/**")
                }
            }

            tasks.register("runServer", JavaExec::class) {
                group = "airflow"
                doNotTrackState("Run server")

                mainClass = "net.minecraft.server.Main"
                classpath = project.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath

                standardInput = System.`in`

                doFirst {
                    workingDir(project.rootDir.resolve("run").apply { mkdirs() })

                    args("--nogui")
                }
            }
        }
    }
}