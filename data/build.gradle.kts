import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.cineverse.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("keys.properties")))

android {
    namespace = "com.moscow.data"

    defaultConfig {
        val bearerToken = localProperties["BEARER_TOKEN"].toString()
        buildConfigField("String", "BEARER_TOKEN", "\"${bearerToken.trim()}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.work.runtime.ktx)

    /** Data Store **/
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Retrofit
    implementation(libs.retrofit)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.bundles.room.testing)

    implementation(libs.gson)
    implementation(libs.kotlinx.datetime)

    implementation(libs.koin.androidx.workmanager)

    //unit testing
    testImplementation (libs.junit)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockk.v11310)


}