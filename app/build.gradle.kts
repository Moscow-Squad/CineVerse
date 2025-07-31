plugins {
    alias(libs.plugins.cineverse.android.application)

    // Firebase & tooling
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.firebase.perf)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.kover)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

android {

    namespace = libs.versions.namespace.get()

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("storeFile") ?: "my-release-key.jks")
            storePassword = System.getProperty("storePassword")
            keyAlias = System.getProperty("keyAlias")
            keyPassword = System.getProperty("keyPassword")
        }
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlytics_enabled"] = "true"
            manifestPlaceholders["analytics_enabled"] = "true"
        }
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
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

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

    implementation(libs.androidx.navigation.compose)
    // Splash
    implementation(libs.androidx.core.splashscreen)
}
