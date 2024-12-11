plugins {
    id("java-library")
}

val minecraftLibrary by configurations.registering

configurations.api {
    extendsFrom(minecraftLibrary.get())
}

dependencies {
    api(libs.bundles.adventure)
    api(libs.configurate)
    api(libs.guice) {
        exclude("com.google.guava", "guava")
    }
}

val generatedDir = "src/generated/java"

sourceSets {
    main {
        java {
            srcDir(generatedDir)
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.register<JavaExec>("runApiGenerator") {
    doNotTrackState("Run api generator")

    mainClass = "me.glicz.airflow.api.generator.Main"
    classpath(project(":api-generator").sourceSets.main.get().runtimeClasspath)

    doFirst {
        workingDir(project.rootDir.resolve("run").apply { mkdirs() })

        val sourceFolder = project.projectDir.resolve(generatedDir).absoluteFile
        sourceFolder.deleteRecursively()

        val debugFlag = if (project.hasProperty("debug")) "-debug" else ""

        args("-sourceFolder=${sourceFolder}", debugFlag)
    }
}