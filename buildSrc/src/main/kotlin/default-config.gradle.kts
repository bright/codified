plugins {
    `java-library`
    kotlin("jvm")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Config.jvmToolchain))
    }
    sourceCompatibility = Config.javaTargetCompatibility
    targetCompatibility = Config.javaTargetCompatibility
}

kotlin {
    compilerOptions {
        jvmTarget = Config.kotlinJvmTarget
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

repositories {
    mavenCentral()
    maven(Repositories.jitpack)
}