import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.Test

plugins {
    kotlin("jvm") version Versions.kotlin
    `maven-publish`
}

allprojects {
    group = Config.group
    version = Config.version

    repositories {
        mavenCentral()
        maven(Repositories.bintrayBright)
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

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register<MavenPublication>("default") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
