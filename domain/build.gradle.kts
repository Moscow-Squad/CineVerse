plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies{
    testImplementation(kotlin("test"))

    implementation(libs.kotlinx.datetime)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)

    testImplementation(libs.truth)
    testImplementation(libs.mockk)
}
tasks.test {
    useJUnitPlatform()
}