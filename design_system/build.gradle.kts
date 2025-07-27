plugins {
    alias(libs.plugins.cineverse.android.compose)
}

android {
    namespace = "com.moscow.cineverse.design_system"
}

dependencies {

    implementation(projects.imageViewer)

    implementation(libs.coil3.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.readmore.material3)

    implementation(libs.accompanist.webview)
}