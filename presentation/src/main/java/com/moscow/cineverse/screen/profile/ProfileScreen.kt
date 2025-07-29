package com.moscow.cineverse.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalScaffoldPaddingValues

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    ProfileContent(modifier)
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .padding(LocalScaffoldPaddingValues.current)
    ) {

    }

}