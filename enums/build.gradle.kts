plugins {
    kotlin("jvm")
    id("default-java-publish")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(rootProject)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
