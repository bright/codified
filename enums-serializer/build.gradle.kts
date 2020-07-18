plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.72"
}

group = Config.group
version = "1.0"

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":enums"))
    api(Dependencies.serialization)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
