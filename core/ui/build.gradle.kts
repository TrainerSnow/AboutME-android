plugins {
    androidPlugins()
}

apply<AndroidComposeLibraryPlugin>()

android {
    namespace = "com.aboutme.core.ui"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}