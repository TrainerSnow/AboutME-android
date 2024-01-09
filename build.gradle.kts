plugins {
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.kotlin.android.get().pluginId) apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.jetbrains.kotlin.kapt") apply false
}
