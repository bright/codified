plugins {
    `maven-publish`
}

publishing {
    publications {
        register<MavenPublication>("default") {
            from(components["java"])
        }
    }
}
