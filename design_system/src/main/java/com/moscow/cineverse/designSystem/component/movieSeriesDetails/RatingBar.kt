package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import com.example.design_system.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieRatingBar(
    rating: Int, onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (i in 1..5) {
            val isFilled = i <= rating
            val targetColor = if (isFilled) Theme.colors.additional.primary.yellow else Theme.colors.shade.tertiary

            val animatedTint by animateColorAsState(
                targetValue = targetColor,
                animationSpec = tween(durationMillis = 300),
                label = "star_tint_animation"
            )
            Icon(
                    if (i <= rating) painterResource(R.drawable.yellow_star) else painterResource(R.drawable.outline_star),
                    contentDescription = stringResource(R.string.star_icon),
                    modifier =
                        Modifier
                        .size(16.dp)
                        .clickable{ onRatingChanged(i.toFloat()) },
                    tint = animatedTint
            )
        }
    }


}

@Preview
@Composable
private fun PreviewRatingBar() {
    CineVerseTheme {
        MovieRatingBar(3, {})
    }
}