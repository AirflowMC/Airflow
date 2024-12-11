import me.glicz.airplane.task.PaperclipJar

plugins {
    id("me.glicz.airplane")
    id("net.kyori.indra.git") version "3.1.3"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-releases/")
}

configurations.apiElements {
    extendsFrom(configurations.implementation.get())
}

dependencies {
    mache(paperMache(properties["mache-build"] as String))
    paperclip(libs.paperclip)
    implementation(project(":api"))
    implementation(libs.adventure.text.serializer.ansi)
    implementation(libs.jline.terminal)
    implementation(libs.terminalConsoleAppender)
    annotationProcessor(libs.log4j.core)
}

val internalsDir = "src/internals/java"

sourceSets {
    main {
        java {
            srcDir(internalsDir)
        }
    }
}

airplane {
    minecraftVersion = properties["minecraft-version"] as String
    sourcesDir = projectDir.resolve(internalsDir)
    patchesDir = projectDir.resolve("patches").apply { mkdirs() }

    // export some of the dependencies to api in order to avoid version mismatch
    exportFilteredDependencies = mapOf(
        project(":api") to listOf(
            "com.google.guava:guava",
            "org.slf4j:slf4j-api",
            "com.mojang:brigadier"
        )
    )
}

tasks {
    jar {
        indraGit.applyVcsInformationToManifest(manifest)
    }
}

afterEvaluate {
    tasks {
        withType<PaperclipJar> {
            dependsOn(":api:jar")
        }

        named<JavaExec>("runServer") {
            project(":test-plugin").tasks.jar.let { testPluginJar ->
                dependsOn(testPluginJar)

                testPluginJar.get().destinationDirectory.set(
                    project.rootDir.resolve("run/plugins").apply {
                        deleteRecursively()
                    }
                )
            }
        }
    }
}