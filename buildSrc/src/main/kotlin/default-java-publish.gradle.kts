plugins {
    `java-library`
    `maven-publish`
    signing
}

group = Config.group
version = Config.version

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register<MavenPublication>("default") {
            from(components["java"])

            pom {
                name.set("Codified")
                description.set("Facilitates codification of objects (mainly enums)")
                url.set("https://github.com/bright/codified")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }

                developers {
                    developer {
                        name.set("Andrzej Zabost")
                        email.set("andrzej.zabost@brightinventions.pl")
                        organization.set("Bright Inventions")
                        organizationUrl.set("https://brightinventions.pl/")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/bright/codified.git")
                    developerConnection.set("scm:git:ssh://github.com:bright/codified.git")
                    url.set("https://github.com/bright/codified/tree/master")
                }
            }
        }

        repositories {
            maven {
                name = "MavenCentralStaging"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials(PasswordCredentials::class)
            }
            maven {
                name = "MavenCentralSnapshots"
                setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                credentials(PasswordCredentials::class)
            }
        }
    }
}

signing {
    sign(publishing.publications)
}