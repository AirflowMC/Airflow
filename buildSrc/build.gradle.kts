plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("codechicken:DiffPatch:1.5.0.30")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
}