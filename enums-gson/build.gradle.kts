plugins {
    kotlin("jvm")
    id("default-config")
    id("default-java-publish")
}

dependencies {
    api(rootProject)
    api(project(":enums"))

    implementation(kotlin("stdlib"))
    implementation(Dependencies.gson)

    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
