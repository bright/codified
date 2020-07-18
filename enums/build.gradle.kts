plugins {
    kotlin("jvm")
}

group = Config.group
version = "1.0"

dependencies {
    implementation(kotlin("stdlib"))
    api(rootProject)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
