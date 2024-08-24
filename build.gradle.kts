plugins {
    id("java")
}

subprojects {
    plugins.apply("java")

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release = 21
        }
    }
}