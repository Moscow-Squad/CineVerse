package com.moscow.cineverse.screen.on_boarding.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.moscow.cineverse.screen.on_boarding.PageUiState

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    pageUiState: PageUiState
) {
    Image(
        modifier = modifier,
        painter = painterResource(pageUiState.imageResId), contentDescription = "",
        contentScale = ContentScale.Crop
    )
}