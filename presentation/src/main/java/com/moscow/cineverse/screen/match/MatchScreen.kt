package com.moscow.cineverse.screen.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalScaffoldPaddingValues

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
) {
    MatchContent(modifier)
}

@Composable
fun MatchContent(
    modifier: Modifier = Modifier,

) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(LocalScaffoldPaddingValues.current)
        ) {}

}