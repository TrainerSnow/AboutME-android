import org.gradle.api.Project
import org.gradle.kotlin.dsl.PluginDependenciesSpecScope

fun Project.androidPlugins(
    application: Boolean = false
) = apply {
    plugin(versionCatalog().getPlugin("kotlin.android"))
    if (application) {
        plugin(versionCatalog().getPlugin("android.application"))
    } else {
        plugin(versionCatalog().getPlugin("android.library"))
    }
}

fun Project.hiltAndroidPlugins(
    application: Boolean = false
) = apply {
    androidPlugins(application)

    plugin(versionCatalog().getPlugin("hilt.android"))
    plugin(versionCatalog().getPlugin("kotlin.kapt"))
}

//TODO: Can't use version catalogs here
fun PluginDependenciesSpecScope.androidPlugins(
    application: Boolean = false
) {
    id("org.jetbrains.kotlin.android")
    if (application) {
        id("com.android.application")
    } else {
        id("com.android.library")
    }
}