import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "io.github.paulgriffith"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.2.1")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.5.0")

    val kotestVersion = "4.6.0"
    testImplementation("io.kotest", "kotest-runner-junit5-jvm", kotestVersion)
    testImplementation("io.kotest", "kotest-assertions-core-jvm", kotestVersion)
    testImplementation("io.kotest", "kotest-property-jvm", kotestVersion)
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
}
