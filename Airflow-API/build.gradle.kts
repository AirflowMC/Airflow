plugins {
    id("java-library")
}

dependencies {
    api(libs.bundles.adventure)
    api(libs.configurate)
    api(libs.guice)
    api("com.google.guava:guava:32.1.2-jre")
    api("org.slf4j:slf4j-api:2.0.9")
    api("com.mojang:brigadier:1.3.10")
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
    withJavadocJar()
}

tasks.register<JavaExec>("runApiGenerator") {
    doNotTrackState("Run api generator")

    mainClass = "me.glicz.airflow.api.generator.Main"
    classpath(project(":api-generator").sourceSets.main.get().runtimeClasspath)

    doFirst {
        val sourceFolder = project.projectDir.resolve(generatedDir).absoluteFile
        sourceFolder.deleteRecursively()

        args("-sourceFolder=${sourceFolder}")
    }
}