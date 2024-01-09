import org.gradle.api.Project

fun Project.androidConfig() = android().apply {

    compileSdk = ProjectConfig.compileSdk

    defaultConfig {

        minSdk = ProjectConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

}

fun Project.composeConfig() = android().apply {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}