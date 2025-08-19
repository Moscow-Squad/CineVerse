package com.moscow.cineverse.screen.details.common


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R
import java.util.Locale

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    title: String,
    genres: String,
    rating: String,
    duration: String,
    releaseDate: String,
    type: String,
    onSaveClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    titleOverFlow: Boolean = false
) {
    val formattedRating = try {
        val ratingValue = rating.toDouble()
        String.format(Locale.getDefault(), "%.1f", ratingValue)
    } catch (_: NumberFormatException) {
        "0.0"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.colors.background.card,
                RoundedCornerShape(Theme.radius.large)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = type,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.brand.primary
            )
            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.colors.shade.primary,
                maxLines = if (titleOverFlow) 1 else Int.MAX_VALUE,
                overflow = if (titleOverFlow) TextOverflow.Ellipsis else TextOverflow.Clip
            )
            Text(
                text = genres,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary,
                maxLines = if (titleOverFlow) 1 else Int.MAX_VALUE,
                overflow = if (titleOverFlow) TextOverflow.Ellipsis else TextOverflow.Clip
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (formattedRating != "0.0")
                    InfoTextWithIcon(
                        R.drawable.due_tone_star,
                        formattedRating,
                        Theme.colors.additional.primary.yellow
                    )
                if (duration.isNotBlank() && duration != "N/A" && duration != "null") {
                    InfoTextWithIcon(
                        R.drawable.due_tone_clock,
                        duration,
                        Theme.colors.shade.secondary
                    )
                }

                if (releaseDate.isNotBlank())
                    InfoTextWithIcon(
                        R.drawable.due_tone_calendar,
                        releaseDate,
                        Theme.colors.shade.secondary
                    )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MovieFloatingButton(
                buttonIcon = R.drawable.due_tone_play,
                onClick = { onPlayClick() },
                backgroundColor = Theme.colors.button.primary,
                iconColor = Theme.colors.brand.tertiary,
                disableQuickClicks = true
            )
            MovieFloatingButton(
                buttonIcon = R.drawable.due_tone_add,
                onClick = { onSaveClick() },
                backgroundColor = Theme.colors.button.secondary,
                iconColor = Theme.colors.shade.primary,
                disableQuickClicks = true
            )
        }
    }
}