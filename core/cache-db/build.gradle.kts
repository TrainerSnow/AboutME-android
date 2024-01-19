plugins {
    androidPlugins()
}
apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.cache"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.google.gson)
    implementation(libs.kotlin.coroutines)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    add("kapt", libs.room.compiler)
}