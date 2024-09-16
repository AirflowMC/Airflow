package me.glicz.airplane.piston

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.glicz.airplane.util.*
import org.gradle.api.Project
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists

object PistonMeta {
    private val gson = Gson().newBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private val client: HttpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build()

    fun downloadServerResources(project: Project, version: String) {
        downloadServerResources(project, fetchVersionDownloads(fetchVersion(version)))
    }

    fun fetchVersion(version: String): Version {
        val request = HttpRequest.newBuilder()
            .uri(VERSION_MANIFEST_URI)
            .GET()
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val jsonObject = gson.fromJson(response.body(), JsonObject::class.java)

        val versionData = jsonObject["versions"].asJsonArray.find { it.asJsonObject["id"].asString == version }
        return Gson().fromJson(versionData, Version::class.java)
    }

    fun fetchVersionDownloads(version: Version): VersionDownloads {
        val request = HttpRequest.newBuilder()
            .uri(URI(version.url))
            .GET()
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val jsonObject = gson.fromJson(response.body(), JsonObject::class.java)

        return gson.fromJson(jsonObject["downloads"], VersionDownloads::class.java)
    }

    fun downloadServerResources(project: Project, downloads: VersionDownloads) {
        downloadResource(project.airplaneDir.resolve(SERVER_BOOSTRAP_JAR).toPath(), downloads.server) {
            project.airplaneBuildDir.deleteRecursively()
        }
        downloadResource(project.airplaneDir.resolve(SERVER_MAPPINGS).toPath(), downloads.serverMappings)
    }

    fun downloadResource(path: Path, resource: VersionDownloads.Resource, hashMismatch: () -> Unit = {}) {
        if (path.exists() && path.toFile().sha1.stringHash == resource.sha1) {
            return
        }

        hashMismatch()

        val request = HttpRequest.newBuilder()
            .uri(URI(resource.url))
            .GET()
            .build()

        path.deleteIfExists()
        path.parent.createDirectories()
        path.createFile()
        client.send(request, BodyHandlers.ofFile(path))
    }
}