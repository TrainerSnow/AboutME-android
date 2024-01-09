plugins {
    id("java-library")
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
}

kotlin {
    jvmToolchain(17)
}