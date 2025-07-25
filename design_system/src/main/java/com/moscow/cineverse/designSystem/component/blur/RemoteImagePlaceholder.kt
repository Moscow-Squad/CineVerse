package com.moscow.cineverse.designSystem.component.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R

@Composable
fun RemoteImagePlaceholder(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()
    val placeholderResId = if (isDarkTheme)
        R.drawable.due_tone_image
    else
        R.drawable.due_tone_image
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = placeholderResId),
            contentDescription = "Remote Image Placeholder",
            contentScale = ContentScale.None,
            modifier = modifier.size(56.dp)
        )
    }
}