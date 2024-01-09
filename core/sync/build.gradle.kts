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

    implementation(libs.android.work)
    implementation(libs.hilt.work)
}