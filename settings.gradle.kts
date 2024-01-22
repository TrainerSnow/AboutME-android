pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "about-me-mobile"
include(":app")
include(":core:network")
include(":core:model")
include(":core:secret")
include(":core:data")
include(":feature:auth")
include(":core:domain")
include(":core:input")
include(":core:common")
include(":core:ui")
include(":feature:home")
include(":feature:profile")
include(":core:cache-db")
include(":core:connectivity")
include(":core:sync")
include(":core:auth")
include(":core:local-db")
include(":core:datastore")
include(":feature:preferences")
