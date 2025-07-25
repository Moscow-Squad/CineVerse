plugins {
    alias(libs.plugins.cineverse.android.application)

    // Firebase & tooling
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.firebase.perf)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.kover)
}

android {

    namespace = libs.versions.namespace.get()

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            manifestPlaceholders["crashlytics_debug"] = "true"
            manifestPlaceholders["analytics_debug"] = "true"
        }
    }
}

firebaseAppDistribution {
    appId = System.getenv("FIREBASE_APP_ID")
    serviceCredentialsFile = "app/service-account-key.json"
    artifactType = "APK"
    groups = "tester"
}

dependencies {
    // Modules
    implementation(projects.designSystem)
    implementation(projects.presentation)
    implementation(projects.data)
    implementation(projects.domain)

    // DI & platform
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)

    // UI extras
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.animation)
    implementation(libs.coil.compose)

    // Background & Navigation
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.androidx.navigation.compose)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
