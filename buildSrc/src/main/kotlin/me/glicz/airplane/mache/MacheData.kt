package me.glicz.airplane.mache

data class MacheData(
    val minecraftVersion: String,
    val macheVersion: String,
    val dependencies: Map<String, List<Dependency>>,
    val repositories: List<Repository>,
    val decompilerArgs: List<String>,
    val remapperArgs: List<String>,
    val additionalCompileDependencies: Map<String, List<Dependency>>
)

data class Dependency(
    val group: String,
    val name: String,
    val version: String,
    val classifier: String?,
    val extension: String?
) {
    val asNotation: String
        get() {
            return buildString {
                append("${group}:${name}:${version}")

                if (classifier != null) {
                    append(":${classifier}")
                }

                if (extension != null) {
                    append("@${extension}")
                }
            }
        }
}

data class Repository(val url: String, val name: String, val groups: List<String>)