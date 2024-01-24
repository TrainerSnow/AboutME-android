plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlin.coroutines)
}