package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun RatingSection(
    icon: Int,
    title: String,
    caption: String,
    onClick: () -> Unit,
    ratingStars : Int,
    modifier: Modifier = Modifier
) {

    val rateTitle = stringResource(R.string.you_rated_it)
    val rateCaption = stringResource(R.string.tap_to_change_your_rating)

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text = stringResource(R.string.did_you_watch_it),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.card),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.shade.quinary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "Icon",
                    tint = Theme.colors.brand.primary,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = if(ratingStars == 0) title else rateTitle,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary
                    )
                    if(ratingStars != 0){
                        MovieRatingBar(
                            rating = ratingStars,
                            onRatingChanged =  {_ ->}
                        )
                    }
                }
                Text(
                    text = if(ratingStars == 0) caption else rateCaption,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }

            IconButton(onClick = onClick) {
                Icon(
                    painter = painterResource(R.drawable.outline_alt_arrow_right),
                    contentDescription = "Arrow Icon",
                    tint = Theme.colors.shade.tertiary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingSectionPreview() {
    CineVerseTheme {
        RatingSection(
            icon = R.drawable.due_tone_magic_stick,
            title = "Give it Stars!",
            caption = "Let the world know how you felt.",
            onClick = {},
            ratingStars = 4
        )
    }
}
