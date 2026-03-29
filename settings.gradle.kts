pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement { repositories { mavenCentral() } }

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

rootProject.name = "nucleus"
