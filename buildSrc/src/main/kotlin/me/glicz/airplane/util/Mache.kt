package me.glicz.airplane.util

import com.google.gson.Gson
import me.glicz.airplane.mache.MacheData
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

@OptIn(ExperimentalPathApi::class)
fun Project.extractMacheArtifact(): MacheData {
    val macheDataDir = airplaneDir.resolve(MACHE_DIR).apply { toPath().deleteRecursively() }

    configurations["mache"].first().unzip(macheDataDir)
    val macheData = Gson().fromJson(airplaneDir.resolve(MACHE_DATA).bufferedReader(), MacheData::class.java)

    macheData.repositories.forEach { repository ->
        repositories.maven(repository.url) {
            name = repository.name

            content {
                repository.groups.forEach {
                    @Suppress("UnstableApiUsage")
                    includeGroupAndSubgroups(it)
                }
            }
        }
    }

    macheData.dependencies.forEach { (key, value) ->
        value.forEach { dependency ->
            dependencies.add(key, dependency.asNotation)
        }
    }

    macheData.additionalCompileDependencies.forEach { (key, value) ->
        value.forEach { dependency ->
            dependencies.add(key, dependency.asNotation)
        }
    }

    return macheData
}