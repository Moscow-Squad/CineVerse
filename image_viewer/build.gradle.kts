plugins {
    alias(libs.plugins.cineverse.android.compose)
}

android {
    namespace = "com.moscow.cineverse.image_viewer"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.tooling.preview.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.coil3.compose)
    implementation(libs.cloudy)

    implementation(libs.tensorflow.lite.vision)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)

}