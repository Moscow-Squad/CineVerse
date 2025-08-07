package com.moscow.cineverse.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun SplashScreen(
    navigateToHome:()->Unit,
    navigateToLogin:()->Unit,
    navigateToOnboarding:()->Unit,
    modifier: Modifier = Modifier) {


    SplashContent(modifier)
}

@Composable
fun SplashContent(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen),
        contentAlignment = Alignment.Center

    ) {

        Image(
            painter = painterResource(R.drawable.cine_verse_logo),
            contentDescription = "splash image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )
    }

}

@Preview
@Composable
private fun SplashPreview() {
    CineVerseTheme {
        SplashScreen(
            {},
            {},
            {}
        )
    }
    
}