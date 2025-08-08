package com.moscow.cineverse.screen.cast_details.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R as PresentationR

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Theme.colors.brand.primary
        )
    }
}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(PresentationR.string.no_content_available),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.regular
        )
    }
}