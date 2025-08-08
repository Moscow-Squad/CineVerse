package com.moscow.cineverse.screen.series_details.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer

@Composable
fun SeasonCard(
    modifier: Modifier = Modifier,
    enableBlur: String,
    seasonNumber: String,
    episodeCount: Int,
    airDate: String,
    posterUrl: String? = null,
    caption: String,
    rate: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SafeImageViewer(
                imageUrl = posterUrl ?: "",
                modifier = Modifier
                    .width(48.dp)
                    .height(80.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.size5xl,
                            topEnd = Theme.radius.size5xl,
                            bottomStart = Theme.radius.extraSmall,
                            bottomEnd = Theme.radius.extraSmall
                        )
                    ),
                isBlurEnabled = enableBlur,
                placeholderContent = {
                    RemoteImagePlaceholder(
                        Modifier
                            .fillMaxSize()
                    )
                },
                errorContent = {
                    RemoteImagePlaceholder(Modifier.fillMaxSize())
                },
            ) {
                OnBlurContent(
                    hintText = stringResource(R.string.unsuitable_image),
                    isAddedText = false
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MovieText(
                    text = seasonNumber,
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.primary
                )
                MovieText(
                    text = caption,
                    style = Theme.textStyle.body.small.regular,
                    color = Theme.colors.shade.secondary,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Theme.colors.stroke.primary)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SeasonInfo(
                icon = R.drawable.due_tone_star,
                iconTint = Theme.colors.additional.primary.yellow,
                iconDescription = stringResource(R.string.rate_icon),
                title = rate
            )
            SeasonInfo(
                icon = R.drawable.due_tone_video_library,
                iconTint = Theme.colors.shade.secondary,
                iconDescription = stringResource(R.string.episodes_icon),
                title = episodeCount.toString() + " " + stringResource(R.string.episodes)
            )
            SeasonInfo(
                icon = R.drawable.due_tone_calendar,
                iconTint = Theme.colors.shade.secondary,
                iconDescription = stringResource(R.string.air_date_icon),
                title = airDate.take(4)
            )
        }
    }
}

@Composable
private fun SeasonInfo(
    @DrawableRes icon: Int,
    iconTint: Color,
    iconDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = iconDescription,
            modifier = Modifier.size(16.dp),
            tint = iconTint
        )
        Text(
            text = title,
            style = Theme.textStyle.label.medium.regular,
            color = Theme.colors.shade.secondary
        )
    }
}