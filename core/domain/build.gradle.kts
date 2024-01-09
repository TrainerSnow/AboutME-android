plugins {
    androidPlugins()
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "com.aboutme.core.domain"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.android.lifecycle)
    implementation(libs.compose.runtime)
    implementation(libs.compose.activity)
}