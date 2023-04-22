plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("default-config")
    id("default-java-publish")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":enums"))
    api(Dependencies.serializationCore)
    testImplementation(Dependencies.serializationJson)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
