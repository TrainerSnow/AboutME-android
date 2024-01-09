plugins {
 androidPlugins()
}

apply<AndroidFeaturePlugin>()

android {
    namespace = "com.aboutme.profile"
}

kotlin {
    jvmToolchain(17)
}