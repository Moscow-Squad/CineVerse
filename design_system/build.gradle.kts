plugins {
    alias(libs.plugins.cineverse.android.compose)
}

android {
    namespace = "com.moscow.cineverse.design_system"
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.material3)
    implementation(libs.readmore.material3)
}