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
    mache(papierMache(properties["mache-build"] as String))
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