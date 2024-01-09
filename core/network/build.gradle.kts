plugins {
    androidPlugins()
    alias(libs.plugins.apollo)
}

apply<AndroidHiltLibraryPlugin>()

android {
    namespace = "com.aboutme.core.network"

    buildTypes {
        all {
            buildConfigField("String", "GRAPHQL_ROOT_URL", "\"http://192.168.42.33:8080/graphql\"")
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.aboutme")

        mapScalarToKotlinLong("Long")
        mapScalar("Date", "java.time.LocalDate", "com.aboutme.network.scalars.DateScalarAdapter")
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project((":core:model")))

    implementation(libs.apollo)
}