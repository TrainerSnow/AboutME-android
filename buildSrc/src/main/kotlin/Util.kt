import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

fun Project.android(): LibraryExtension = extensions.getByType(LibraryExtension::class.java)

fun Project.versionCatalog(): VersionCatalog =
    extensions.getByType(VersionCatalogsExtension::class.java).find("libs").get()

fun MinimalExternalModuleDependency.toFormattedDependency() = "${group}:${name}:${version}"

fun VersionCatalog.getLibrary(qualifier: String): String =
    findLibrary(qualifier).get().get().toFormattedDependency()

fun VersionCatalog.getPlugin(qualifier: String): String = findPlugin(qualifier).get().get().pluginId

fun ExternalModuleDependencyBundle.addAllTo(project: Project) = forEach {
    project.dependencies.add("implementation", it.toFormattedDependency())
}