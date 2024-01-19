plugins {
    androidPlugins()
    alias(libs.plugins.google.protobuf)
}

apply<AndroidLibraryPlugin>()

android {
    namespace = "com.aboutme.core.datastore"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.android.datastore)
    implementation(libs.google.protobuf)
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