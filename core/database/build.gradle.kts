plugins {
    androidPlugins()
}
apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.database"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.google.gson)
    implementation(libs.kotlin.coroutines)

    implementation(libs.room.runtime)
    add("kapt", libs.room.compiler)
}