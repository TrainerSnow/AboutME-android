plugins {
    androidPlugins()
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.data"
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(libs.kotlin.coroutines)

    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:secret"))
    implementation(project(":core:common"))
    implementation(project(":core:sync"))
    implementation(project(":core:auth"))
    implementation(project(":core:cache-db"))
    implementation(project(":core:datastore"))

}