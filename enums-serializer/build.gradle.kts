plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("default-java-publish")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":enums"))
    api(Dependencies.serialization)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
