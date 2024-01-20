plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.sync"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:cache-db"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:secret"))
    implementation(project(":core:auth"))
    implementation(project(":core:local-db"))

    implementation(libs.android.work)
    implementation(libs.hilt.work)

    implementation(libs.google.gson)

    add("kapt", "androidx.hilt:hilt-compiler:1.1.0")
}