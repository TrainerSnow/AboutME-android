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