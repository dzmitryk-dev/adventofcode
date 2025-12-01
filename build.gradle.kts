// Shared configuration for all projects
allprojects {
    group = "adventofcode"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

// Test task that runs tests for all included builds
tasks.register("testAll") {
    description = "Run tests for all included builds (utils, 2024, 2025)"
    dependsOn(
        gradle.includedBuild("utils").task(":test"),
        gradle.includedBuild("2024").task(":test"),
        gradle.includedBuild("2025").task(":test")
    )
}
