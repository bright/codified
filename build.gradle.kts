import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    id("default-java-publish")
}

allprojects {
    group = Config.group
    version = Config.version

    repositories {
        mavenCentral()
        maven(Repositories.jitpack)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Config.jvmTarget
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
