package com.moscow.cineverse.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.details.common.InfoSection
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun MediaPosterCard(
    modifier: Modifier = Modifier,
    mediaItem: MediaItemUiState,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMediaItemClick: (Int) -> Unit = {},
    enableBlur: String,
    titleTextAlign: TextAlign = TextAlign.Start,
    showTitle: Boolean = true,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
    useFixedHeight: Boolean = false,
) {
    when (viewMode) {
        ViewMode.GRID -> GridMediaCard(
            modifier = modifier,
            movie = mediaItem,
            showRating = showRating,
            onMovieClick = onMediaItemClick,
            enableBlur = enableBlur,
            titleTextAlign = titleTextAlign,
            showTitle = showTitle,
            showBackdrop = showBackdrop,
            useFixedHeight = useFixedHeight,
            getTitleOverride = getTitleOverride
        )

        ViewMode.LIST -> ListMediaItemCard(
            modifier = modifier,
            mediaItem = mediaItem,
            onMediaItemClick = onMediaItemClick,
            enableBlur = enableBlur,
            getTitleOverride = getTitleOverride
        )
    }
}

@Composable
private fun RemoteImagePlaceholder(
    modifier: Modifier = Modifier,
    cardColor: Color = Theme.colors.brand.tertiary,
    iconSize: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .background(
                color = cardColor,
                shape = RoundedCornerShape(
                    topStart = Theme.radius.large,
                    topEnd = Theme.radius.large,
                    bottomStart = Theme.radius.large
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.due_tone_image),
            contentDescription = "Movie Poster",
            tint = Theme.colors.brand.secondary,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun GridMediaCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    showTitle: Boolean = true,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMovieClick: (Int) -> Unit,
    enableBlur: String,
    titleTextAlign: TextAlign,
    useFixedHeight: Boolean = false,
    getTitleOverride: ((MediaItemUiState) -> String)? = null
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (useFixedHeight) {
                        Modifier.height(180.dp)
                    } else {
                        Modifier.aspectRatio(0.75f)
                    }
                )
                .clip(RoundedCornerShape(Theme.radius.large)),
            shape = RoundedCornerShape(Theme.radius.large),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
        ) {
            Box {
                val imageUrl = if (showBackdrop) movie.backdropPath else movie.posterPath

                SafeImageViewer(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Theme.radius.large))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onMovieClick(movie.id) },
                    isBlurEnabled = enableBlur,
                    placeholderContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier.fillMaxSize(),
                            cardColor = Theme.colors.background.card,
                            iconSize = 32.dp
                        )
                    },
                    errorContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier.fillMaxSize(),
                            cardColor = Theme.colors.background.card,
                            iconSize = 32.dp
                        )
                    }
                ) {
                    OnBlurContent()
                }

                if (showRating && movie.rating >= 0) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        shape = CircleShape,
                        color = Theme.colors.background.card.copy(alpha = 0.9f),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "%.1f".format(movie.rating),
                                color = Theme.colors.shade.primary,
                                style = Theme.textStyle.label.medium.medium
                            )
                            Icon(
                                painter = painterResource(R.drawable.due_tone_star),
                                contentDescription = "Rating",
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        if (showTitle) {
            val displayTitle = getTitleOverride?.invoke(movie) ?: movie.title
            if (displayTitle.isNotEmpty()) {
                Text(
                    text = displayTitle,
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.medium.medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = titleTextAlign,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onMovieClick(movie.id) }
                        )
                )
            }
        }
    }
}

@Composable
private fun ListMediaItemCard(
    modifier: Modifier = Modifier,
    mediaItem: MediaItemUiState,
    onMediaItemClick: (Int) -> Unit,
    enableBlur: String,
    getTitleOverride: ((MediaItemUiState) -> String)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .clickable { onMediaItemClick(mediaItem.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(Theme.radius.large),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                SafeImageViewer(
                    imageUrl = mediaItem.posterPath,
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.large,
                                topEnd = Theme.radius.large,
                                bottomStart = Theme.radius.large
                            )
                        ),
                    isBlurEnabled = enableBlur,
                    placeholderContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight(),
                            cardColor = Theme.colors.brand.tertiary,
                            iconSize = 24.dp
                        )
                    },
                    errorContent = {
                        RemoteImagePlaceholder(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight(),
                            cardColor = Theme.colors.brand.tertiary,
                            iconSize = 24.dp
                        )
                    }
                ) {
                    OnBlurContent(isAddedText = false)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.5.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    InfoSection(
                        title = getTitleOverride?.invoke(mediaItem) ?: mediaItem.title,
                        genres = mediaItem.genres,
                        paddingTop = 4.dp,
                        rating = mediaItem.rating
                    )

                    DurationAndDateSection(
                        releaseDate = mediaItem.releaseDate?.formatDate() ?: ""
                    )
                }
            }
        }
    }
}

@Composable
private fun DurationAndDateSection(
    modifier: Modifier = Modifier,
    releaseDate: String
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (releaseDate.isNotEmpty()) {
            Icon(
                painter = painterResource(R.drawable.due_tone_calendar),
                contentDescription = "Release Date",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = releaseDate,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.shade.secondary,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}