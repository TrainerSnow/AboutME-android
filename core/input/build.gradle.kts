plugins {
    androidPlugins()
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "com.snow.core.input"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.material3)
}