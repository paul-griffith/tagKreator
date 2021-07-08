import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    val kotlinVersion = "1.5.20"
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

application {
    mainClass.set("io.github.paulgriffith.tagkreator.MainKt")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlin.time.ExperimentalTime",
            )
        }
    }
}
