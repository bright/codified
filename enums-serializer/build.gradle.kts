plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("maven-publish")
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
    api(project(":enums"))
    api(Dependencies.serialization)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
