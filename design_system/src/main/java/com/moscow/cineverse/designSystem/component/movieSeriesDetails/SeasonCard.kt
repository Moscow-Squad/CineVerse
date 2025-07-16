package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme


@Composable
fun SeasonCard(
    modifier: Modifier = Modifier,
    season: Season
) {
    Column(
        modifier = modifier
            .width(328.dp)
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
            val isPreview = LocalInspectionMode.current
            if (isPreview) {
                Image(
                    painter = painterResource(R.drawable.profile_image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .width(48.dp)
                        .heightIn(64.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.size5xl,
                                topEnd = Theme.radius.size5xl,
                                bottomStart = Theme.radius.extraSmall,
                                bottomEnd = Theme.radius.extraSmall
                            )
                        ),
                    contentScale = ContentScale.Crop
                )
            } else {
                SafeImageViewer(
                    model = season.posterUrl,
                    contentDescription = stringResource(R.string.season_poster),
                    fallback = painterResource(R.drawable.due_tone_image),
                    modifier = Modifier
                        .width(48.dp)
                        .heightIn(64.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.size5xl,
                                topEnd = Theme.radius.size5xl,
                                bottomStart = Theme.radius.extraSmall,
                                bottomEnd = Theme.radius.extraSmall
                            )
                        ),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.season, season.seasonNumber),
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.primary
                )
                Text(
                    text = season.caption,
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
                title = season.rate.toString()
            )
            SeasonInfo(
                icon = R.drawable.due_tone_video_library,
                iconTint = Theme.colors.shade.secondary,
                iconDescription = stringResource(R.string.episodes_icon),
                title = stringResource(R.string.episodes, season.episodeCount)
            )
            SeasonInfo(
                icon = R.drawable.due_tone_calendar,
                iconTint = Theme.colors.shade.secondary,
                iconDescription = stringResource(R.string.air_date_icon),
                title = season.airDate
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

@Preview
@Composable
private fun SeasonCardPreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(16.dp),
        ) {
            SeasonCard(
                season = Season(
                    seasonNumber = 1,
                    episodeCount = 10,
                    airDate = "2019",
                    posterUrl = "https://www.dreamworks.com/storage/cms-uploads/the-wild-robot-poster4.jpg",
                    caption = "Season 1 of the series",
                    rate = 8.5f
                )
            )
        }
    }
}