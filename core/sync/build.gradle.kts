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
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:secret"))
    implementation(project(":core:auth"))

    implementation(libs.android.work)
    implementation(libs.hilt.work)

    add("kapt", "androidx.hilt:hilt-compiler:1.1.0")
}