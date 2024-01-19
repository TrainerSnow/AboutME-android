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

dependencies {
    implementation(project(":core:sync"))
    implementation(project(":core:auth"))
}