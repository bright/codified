import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {
    val group = "dev.bright.codified"
    val version = "1.9.24.1"

    /**
     * Since Gradle 8.8 a toolchain can also be set for the Gradle daemon (with some limitations)
     * See [the docs](https://docs.gradle.org/8.8/userguide/gradle_daemon.html#sec:daemon_jvm_criteria) for more info
     */
    val jvmToolchain = 21
    val javaTargetCompatibility = JavaVersion.VERSION_1_8
    val kotlinJvmTarget = JvmTarget.fromTarget(javaTargetCompatibility.toString())
}