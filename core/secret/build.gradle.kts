plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.secret"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.android.security.crypto)
}