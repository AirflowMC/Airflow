package me.glicz.airplane.piston

data class Version(val id: String, val type: Type, val url: String) {
    enum class Type {
        RELEASE,
        SNAPSHOT
    }
}

data class VersionDownloads(val server: Resource, val serverMappings: Resource) {
    data class Resource(val sha1: String, val size: Long, val url: String)
}
