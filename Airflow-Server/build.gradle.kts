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

airplane {
    minecraftVersion = properties["minecraft-version"] as String
    sourcesDir = projectDir.resolve(internalsDir)
    patchesDir = projectDir.resolve("patches").apply { mkdirs() }
    exportFilteredDependencies = mapOf(
        project(":airflow-api") to listOf(
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
        "runServer" {
            project(":test-plugin").tasks.jar.let { testPluginJar ->
                dependsOn(testPluginJar)

                testPluginJar.get().destinationDirectory.set(project.rootDir.resolve("run/plugins"))
            }
        }
    }
}