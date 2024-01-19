plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.datastore"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.android.datastore)
}