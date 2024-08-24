import java.util.*

rootProject.name = "Airflow"

listOf("Airflow-API", "Airflow-Server").forEach { project ->
    val name = project.lowercase(Locale.ENGLISH)
    include(name)
    findProject(":$name")?.projectDir = file(project)
}
