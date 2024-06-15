plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "codified"
include("enums")
include("enums-serializer")
include("enums-jackson")
include("enums-gson")
