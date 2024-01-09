plugins {
    androidPlugins()
}

apply<AndroidFeaturePlugin>()

android {
    namespace = "com.aboutme.feature.auth"
}

kotlin {
    jvmToolchain(17)
}