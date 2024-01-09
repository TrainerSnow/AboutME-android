plugins {
    androidPlugins()
}

apply<AndroidFeaturePlugin>()

android {
    namespace = "com.aboutme.feature.home"
}

kotlin {
    jvmToolchain(17)
}