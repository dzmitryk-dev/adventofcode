plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "adventofcode"

// Include included builds (composite build)
includeBuild("utils")
includeBuild("2024")
includeBuild("2025")
