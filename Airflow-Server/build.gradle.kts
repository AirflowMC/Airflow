import me.glicz.airflow.plugin.papierMache

plugins {
    id("me.glicz.airflow.plugin")
    id("net.kyori.indra.git") version "3.1.3"
}

repositories {
    mavenLocal() // TODO mache
}

dependencies {
    implementation(project(":airflow-api"))
}

val internalsDir = "src/internals/java"

sourceSets {
    main {
        java {
            srcDir(internalsDir)
        }
    }
}

airflow {
    minecraftVersion = properties["minecraft-version"] as String
    mache = papierMache(properties["mache-build"] as String)
    sourcesDir = File(internalsDir)
    patchesDir = projectDir.resolve("patches").apply { mkdirs() }
}

tasks {
    jar {
        indraGit.applyVcsInformationToManifest(manifest)
    }
}

afterEvaluate {
    tasks {
        "runServer" {
            project(":test-plugin").tasks.jar.let { testPluginJar ->
                dependsOn(testPluginJar)

                testPluginJar.get().destinationDirectory.set(project.rootDir.resolve("run/plugins"))
            }
        }
    }
}