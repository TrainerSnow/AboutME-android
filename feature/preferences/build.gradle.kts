plugins {
    androidPlugins()
}

apply<AndroidComposeLibraryPlugin>()

android {
    namespace = "com.aboutme.feature.preferences"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}