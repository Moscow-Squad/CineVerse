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
    implementation(libs.bundles.koin)
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    // Firebase
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)
    //material 3
    implementation(libs.material3)
    // Background
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koin.androidx.workmanager)
    //navigation
    implementation(libs.androidx.navigation.compose)
    // Splash
    implementation(libs.androidx.core.splashscreen)
}
