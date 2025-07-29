plugins {
    alias(libs.plugins.cineverse.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.moscow.cinverse.presentation"
}

dependencies {

    implementation (projects.designSystem)
    implementation(projects.domain)

    implementation(libs.coil3.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material3)
    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    //kotlin date
    implementation(libs.kotlinx.datetime)
    //paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    //navigation
    implementation(libs.androidx.navigation.compose)
}