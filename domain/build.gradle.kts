plugins {
    alias(libs.plugins.cineverse.kotlin)
}

dependencies{
    //kotlin datetime
    implementation(libs.kotlinx.datetime)

    //Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
