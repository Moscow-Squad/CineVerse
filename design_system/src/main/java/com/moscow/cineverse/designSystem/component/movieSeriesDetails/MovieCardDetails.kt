package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.component.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieCardDetails(
    modifier: Modifier = Modifier,
    title: String,
    genres: String,
    rating: String,
    duration: String,
    releaseDate: String,
    posterUrl: String,
    type: String,
    onSaveClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val isPreview = LocalInspectionMode.current
        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.profile_image),
                contentDescription = "Image",
                modifier = Modifier
                    .height(289.dp)
                    .width(216.dp)
                    .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                contentScale = ContentScale.Crop
            )
        } else {
            SafeImageViewer(
                model = posterUrl,
                contentDescription = "Image",
                modifier = Modifier
                    .height(289.dp)
                    .width(216.dp)
                    .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                contentScale = ContentScale.Crop
            )
        }
        DetailCard(
            modifier,
            title,
            genres,
            rating,
            duration,
            releaseDate,
            type,
            onSaveClick,
            onPlayClick
        )
    }
}

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
    onPlayClick: () -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.colors.background.card,
                RoundedCornerShape(Theme.radius.large)
            )
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                color = Theme.colors.shade.primary
            )

            Text(
                text = genres,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoTextWithIcon(
                    R.drawable.due_tone_star,
                    rating,
                    Theme.colors.additional.primary.yellow
                )
                InfoTextWithIcon(R.drawable.due_tone_clock, duration, Theme.colors.shade.secondary)
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
                R.drawable.due_tone_play,
                { onPlayClick() },
                Theme.colors.button.primary,
                Theme.colors.brand.tertiary,
            )
            MovieFloatingButton(
                R.drawable.due_tone_add,
                { onSaveClick() },
                Theme.colors.button.secondary,
                Theme.colors.shade.primary,
            )
        }
    }
}

@Composable
fun InfoTextWithIcon(icon: Int, text: String, tint: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Info Text With Icon",
            tint = tint
        )
        Text(
            text = text,
            style = Theme.textStyle.label.medium.regular,
            color = Theme.colors.shade.secondary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailCardPreview() {
    DetailCard(
        title = "Supernatural",
        genres = "Drama, Mystery, Sci-Fi & Fantasy",
        rating = "8.5",
        duration = "2h 32m",
        releaseDate = "2008, Jul 18",
        type = "SERIES",
        )
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MovieCardDetails(
        posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        title = "Supernatural",
        genres = "Drama, Mystery, Sci-Fi & Fantasy",
        rating = "8.5",
        duration = "2h 32m",
        releaseDate = "2008, Jul 18",
        type = "SERIES"
    )
}




