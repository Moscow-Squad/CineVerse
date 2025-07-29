import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.cineverse.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hiltAndroid)
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

    /** DaggerHilt */
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

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
    testImplementation(libs.junit.jupiter)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.bundles.room.testing)

    implementation(libs.gson)
    implementation(libs.kotlinx.datetime)



    //unit test
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation (libs.junit)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockk.v11310)

}