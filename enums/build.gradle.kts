plugins {
    kotlin("jvm")
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
    api(rootProject)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.shouldko)
}
