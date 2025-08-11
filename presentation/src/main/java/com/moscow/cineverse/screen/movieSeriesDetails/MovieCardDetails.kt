package com.moscow.cineverse.screen.movieSeriesDetails

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.component.button.MovieFloatingButton
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cinverse.presentation.R

@OptIn(ExperimentalSharedTransitionApi::class)
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
    enableBlur: String,
    onSaveClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "Movie Card Details"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                ),
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
                    imageUrl = posterUrl,
                    modifier = Modifier
                        .height(289.dp)
                        .width(216.dp)
                        .clip(RoundedCornerShape(Theme.radius.extraLarge)),
                    isBlurEnabled = enableBlur,
                    placeholderContent = { RemoteImagePlaceholder() },
                    errorContent = { RemoteImagePlaceholder() },
                    onBlurContent = {
                        OnBlurContent()
                    }
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
    onPlayClick: () -> Unit = {},
) {
    val formattedRating = try {
        val ratingValue = rating.toDouble()
        String.format("%.1f", ratingValue)
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
                color = Theme.colors.shade.primary
            )

            Text(
                text = genres,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
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
            )
            MovieFloatingButton(
                buttonIcon = R.drawable.due_tone_add,
                onClick = { onSaveClick() },
                backgroundColor = Theme.colors.button.secondary,
                iconColor = Theme.colors.shade.primary,
            )
        }
    }
}


@Composable
fun InfoTextWithIcon(icon: Int, text: String, tint: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tint
        )
        Text(
            text = text,
            style = Theme.textStyle.label.medium.regular,
            color = Theme.colors.shade.secondary
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainMovieCard(
    enableBlur: String,
    posterUrl: String,
    title: String,
    onSaveClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "Main Movie Card"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
        ) {
            SafeImageViewer(
                imageUrl = posterUrl,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.full)),
                isBlurEnabled = enableBlur,
                placeholderContent = {
                    RemoteImagePlaceholder(
                        modifier = Modifier.fillMaxSize()
                    )
                },
                errorContent = {
                    RemoteImagePlaceholder(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            ) {
                OnBlurContent(isAddedText = false)
            }
            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            )

            MovieFloatingButton(
                buttonIcon = R.drawable.due_tone_add,
                onClick = { onSaveClick() },
                backgroundColor = Theme.colors.button.secondary,
                iconColor = Theme.colors.shade.primary,
                modifier = Modifier.size(40.dp)
            )
            MovieFloatingButton(
                buttonIcon = R.drawable.due_tone_play,
                onClick = { onPlayClick() },
                backgroundColor = Theme.colors.button.primary,
                iconColor = Theme.colors.brand.tertiary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailCardPreview() {
    DetailCard(
        title = "Supernatural",
        genres = "Drama, Mystery, Sci-Fi & Fantasy",
        rating = "8.531",
        duration = "2h 32m",
        releaseDate = "2008, Jul 18",
        type = "SERIES",
    )
}