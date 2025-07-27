package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun AnimatedRatingDisplaySection(
    rating: Float,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Surface(
        modifier = modifier
            .padding(8.dp),
        shape = CircleShape,
        color = Theme.colors.background.card
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "%.1f".format(rating),
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.label.medium.medium,
                modifier = Modifier.alpha(alpha)
            )
            Icon(
                painter = painterResource(R.drawable.due_tone_star),
                contentDescription = "Rating",
                tint = Theme.colors.additional.primary.yellow,
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 4.dp)
                    .alpha(alpha)
            )
        }
    }
}