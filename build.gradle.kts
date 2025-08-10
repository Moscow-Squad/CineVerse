// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.firebase.firebase.perf) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.hiltAndroid ) apply false
}

val excludedPackages = listOf(
    "*.R",
    "*.R_*",
    "**.di.**",
    "*.BuildConfig*",
    "*.Manifest*",
    "*.ComposableSingletons*",
    "*.MainActivity*",
    "*.CineVerseApp*",
    "com.moscow.cineverse.image_viewer*",
    "com.moscow.cineverse.design_system*",
    "entity.**",
    "**.dao.**",
    "**.dto.**",
    "**.response.**",
    "exceptions.**",
)
allprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
    kover {
        reports {
            filters {
                excludes { classes(excludedPackages) }
            }
            total {
                verify {
                    rule { bound { minValue = 0 } }
                }
            }
        }
    }
}

dependencies {
    kover(projects.app)
    kover(projects.data)
    kover(projects.domain)
    kover(projects.presentation)
}
