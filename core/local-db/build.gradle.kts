plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlin.coroutines)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    add("kapt", libs.room.compiler)
}