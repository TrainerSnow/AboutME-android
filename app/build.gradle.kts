import java.util.Properties

plugins {
    androidPlugins(true)
    id(libs.plugins.hilt.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "com.aboutme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aboutme"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:profile"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(libs.compose.activity)
    implementation(libs.android.ui)
    implementation(libs.material3)
    implementation(libs.android.preview)
    implementation(libs.android.splash)
    implementation(libs.compose.navigation)
    implementation(libs.material.icons)
    implementation(libs.material.navigation)
    implementation(libs.material.adaptive)
    implementation(libs.material.windowsizeclass)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.android.work)
    implementation(libs.hilt.work)
}