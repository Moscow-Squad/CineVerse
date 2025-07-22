import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("keys.properties")))

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testRunner.get()
        consumerProguardFiles("consumer-rules.pro")

        val apiKey = localProperties["TMDB_API_KEY"].toString()
        buildConfigField("String", "TMDB_API_KEY", "\"${apiKey.trim()}\"")
        buildConfigField("String", "TMDB_URL", "\"https://api.themoviedb.org/3/\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildConfigField("String", "BearerToken", "\"${properties.getProperty("BearerToken")}\"")

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        val javaVer = JavaVersion.toVersion(libs.versions.javaVersion.get())
        sourceCompatibility = javaVer
        targetCompatibility = javaVer
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }

}

dependencies {
    implementation(projects.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

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
    implementation(libs.gson)
    implementation(libs.kotlinx.datetime)

    implementation(libs.koin.androidx.workmanager)

}