plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.connectivity"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlin.coroutines)
}