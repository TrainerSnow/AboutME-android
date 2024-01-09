import org.gradle.api.Project
import org.gradle.kotlin.dsl.project

fun Project.hiltDependencies() {
    project.dependencies.add("implementation", versionCatalog().getLibrary("hilt.android"))
    project.dependencies.add("kapt", versionCatalog().getLibrary("hilt.compiler"))
}

fun Project.composeDependencies() {
    versionCatalog().findBundle("compose").get().get().addAllTo(this)
}

fun Project.featureDependencies() {
    project.dependencies.add("implementation", project(":core:domain"))
    project.dependencies.add("implementation", project(":core:model"))
    project.dependencies.add("implementation", project(":core:input"))
    project.dependencies.add("implementation", project(":core:data"))
    project.dependencies.add("implementation", project(":core:common"))
    project.dependencies.add("implementation", project(":core:ui"))
}