plugins {
    kotlin("jvm") version Versions.kotlin
    id("default-config")
    id("default-java-publish")
}

dependencies {
    implementation(kotlin("stdlib"))
}
