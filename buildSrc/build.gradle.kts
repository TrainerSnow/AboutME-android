plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    implementation("com.android.tools.build:gradle:8.4.0-alpha03")

    implementation("com.squareup:javapoet:1.13.0")
}

kotlin {
    jvmToolchain(17)
}