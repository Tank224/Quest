pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.google.com/")
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

rootProject.name = "Quest"
include(":app")
 