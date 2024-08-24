import me.glicz.airflow.plugin.papierMache

plugins {
    id("me.glicz.airflow.plugin")
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