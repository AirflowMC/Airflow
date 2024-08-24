package me.glicz.airflow.plugin.util

import com.google.gson.Gson
import me.glicz.airflow.plugin.AirflowExtension
import me.glicz.airflow.plugin.extension.unzip
import me.glicz.airflow.plugin.mache.MacheData
import org.gradle.api.Project
import org.gradle.kotlin.dsl.maven
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

@OptIn(ExperimentalPathApi::class)
fun Project.downloadMacheArtifact(): MacheData {
    val airflowExt = extensions.getByType(AirflowExtension::class.java)

    val configuration = configurations.detachedConfiguration(
        project.dependencies.create(airflowExt.mache.get())
    )
    val artifact = configuration.resolvedConfiguration.resolvedArtifacts.first()

    artifact.file.unzip(airflowDir.resolve(MACHE_DIR).apply { toPath().deleteRecursively() })

    val macheData = Gson().fromJson(airflowDir.resolve(MACHE_DATA).bufferedReader(), MacheData::class.java)

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