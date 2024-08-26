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

java {
    withJavadocJar()
}