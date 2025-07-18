import com.google.firebase.appdistribution.gradle.firebaseAppDistribution

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.firebase.perf)
    alias(libs.plugins.google.firebase.appdistribution)
    jacoco
}

android {
    namespace = "com.moscow.cineverse"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.moscow.cineverse"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

jacoco {

    toolVersion = "0.8.12"
}
subprojects {
    afterEvaluate {
        tasks.withType<Test> {
            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
            }
        }
    }
}
tasks.register<JacocoReport>("testCoverage") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/testCoverage/testCoverage.xml").get().asFile)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html").get().asFile)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*_MembersInjector.class",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*"
    )
    val debugTree = fileTree(mapOf(
        "dir" to layout.buildDirectory.dir("intermediates/classes/debug").get().asFile,
        "excludes" to fileFilter
    ))
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(mapOf(
        "dir" to layout.buildDirectory.get().asFile,
        "includes" to listOf("jacoco/testDebugUnitTest.exec")
    )))
}

firebaseAppDistribution {
    appId = System.getenv("FIREBASE_APP_ID")
    serviceCredentialsFile = "service-account-key.json"
    artifactType = "APK"

    groups = "tester"

}


dependencies {

    implementation(project(":design_system"))
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.koin.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.animation)
    implementation (libs.coil.compose)

    /** Koin */
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    /** work manager */
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koin.androidx.workmanager)
    //navigation
    implementation(libs.androidx.navigation.compose)
}