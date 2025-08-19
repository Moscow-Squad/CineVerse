plugins {
    alias(libs.plugins.cineverse.android.compose)
}

android {
    namespace = "com.moscow.cineverse.image_viewer"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    api(libs.coil3.compose)
    implementation(libs.cloudy)
    implementation(libs.tensorflow.lite)
}