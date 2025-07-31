package com.moscow.cineverse.screen.match.composable

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun ProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    progressColor: Color = Theme.colors.brand.primary,
    backgroundOfProgress: Color = Theme.colors.background.card,
    gapSize: Dp = 8.dp
) {
    var progressValue by remember(progress) { mutableFloatStateOf(0f) }
    val size by animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(backgroundOfProgress)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .fillMaxHeight()
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(progressColor)
                .animateContentSize()
                .padding(gapSize)
        )
    }

    LaunchedEffect(true) {
        progressValue = progress
    }
}

@Preview(
    name = "Progress Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Progress Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ProgressIndicatorPreview() {
    CineVerseTheme {
        ProgressIndicator(
            progress = 0.8f
        )
    }
}