plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation("com.adventofcode:aoc-utils:1.0.0")

    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertj.core)
}

application {
    mainClass.set("adventofcode2024.day1.Day1Kt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}