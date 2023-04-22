plugins {
    kotlin("jvm")
    id("default-config")
    id("default-java-publish")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(rootProject)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
