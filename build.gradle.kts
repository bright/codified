import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.Test

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin"))
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven(Repositories.bintrayBright)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.6"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

plugins {
    kotlin("jvm") version Versions.kotlin
}

group = Config.group
version = "1.0"

dependencies {
    implementation(kotlin("stdlib"))
}
