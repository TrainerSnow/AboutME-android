plugins {
    androidPlugins()
    alias(libs.plugins.google.protobuf)
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
    implementation(libs.google.protobuf)

    implementation(project(":core:model"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.2"
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java")
            }
        }
    }
}