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
    //Data Store
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
    //Koin
    implementation(libs.bundles.koin)
    //Retrofit
    implementation(libs.retrofit)
    //OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //gson
    implementation(libs.gson)
    //work manager
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.androidx.work.runtime.ktx)
    //date time
    implementation(libs.kotlinx.datetime)
}