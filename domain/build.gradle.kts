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
    //kotlin datetime
    implementation(libs.kotlinx.datetime)
    //junit & truth
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    //coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.core)
}
tasks.test {
    useJUnitPlatform()
}