package com.moscow.cineverse.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.mapper.formatDate
import com.moscow.cineverse.screen.details.common.InfoSection
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@OptIn(ExperimentalSharedTransitionApi::class)
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
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope? = null,
) {
    val posterKey = "movie_poster_${mediaItem.id}"
    val titleKey = "movie_title_${mediaItem.id}"
    val ratingKey = "movie_rating_${mediaItem.id}"
    val detailsKey = "movie_details_${mediaItem.id}"

    when (viewMode) {
        ViewMode.GRID -> GridMovieCard(
            modifier = modifier,
            movie = mediaItem,
            showRating = showRating,
            onMovieClick = onMediaItemClick,
            enableBlur = enableBlur,
            titleTextAlign = titleTextAlign,
            showTitle = showTitle,
            showBackdrop = showBackdrop,
            useFixedHeight = useFixedHeight,
            getTitleOverride = getTitleOverride,
            posterKey = posterKey,
            titleKey = titleKey,
            ratingKey = ratingKey,
            detailsKey = detailsKey,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )

        ViewMode.LIST -> ListMovieCard(
            modifier = modifier,
            movie = mediaItem,
            onMovieClick = onMediaItemClick,
            enableBlur = enableBlur,
            getTitleOverride = getTitleOverride,
            posterKey = posterKey,
            titleKey = titleKey,
            ratingKey = ratingKey,
            detailsKey = detailsKey,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
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
            tint = Theme.colors.shade.secondary,
            modifier = Modifier.size(iconSize)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GridMovieCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    showTitle: Boolean = true,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMovieClick: (Int) -> Unit,
    enableBlur: String,
    titleTextAlign: TextAlign,
    useFixedHeight: Boolean = false,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
    posterKey: String,
    titleKey: String,
    ratingKey: String,
    detailsKey: String,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope?
) {
    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
        with(sharedTransitionScope) {
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
                        .sharedElement(
                        sharedContentState = rememberSharedContentState(key = posterKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                        .clip(RoundedCornerShape(Theme.radius.large)),
                    shape = RoundedCornerShape(Theme.radius.large),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
                ) {
                    Box {

                        SafeImageViewer(
                            imageUrl = movie.posterPath,
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

                        if ( showRating && movie.rating >= 0 )
                        {
                            with(sharedTransitionScope) {
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState(key = ratingKey),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        ),
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
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = titleKey),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { onMovieClick(movie.id) }
                                )
                        )
                    }
                }

                AnimatedVisibility(
                    visible = false,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    with(sharedTransitionScope) {
                        DurationAndDateSection(
                            releaseDate = movie.releaseDate?.formatDate() ?: "",
                            modifier = Modifier.sharedElement(
                                sharedContentState = rememberSharedContentState(key = detailsKey),
                                animatedVisibilityScope = this@AnimatedVisibility,
                            )
                        )
                    }
                }
            }
        }
    }
    else{
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

                    SafeImageViewer(
                        imageUrl = movie.posterPath,
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
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ListMovieCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    onMovieClick: (Int) -> Unit,
    enableBlur: String,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
    posterKey: String,
    titleKey: String,
    ratingKey: String,
    detailsKey: String,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope?
) {
    if (sharedTransitionScope != null && animatedVisibilityScope != null){
        with(sharedTransitionScope) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
                    .clickable { onMovieClick(movie.id) },
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(Theme.radius.large),
                colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight()
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = posterKey),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                ).clip(
                                    RoundedCornerShape(
                                        topStart = Theme.radius.large,
                                        topEnd = Theme.radius.large,
                                        bottomStart = Theme.radius.large
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            SafeImageViewer(
                                imageUrl = movie.posterPath,
                                modifier = Modifier
                                    .fillMaxSize(),
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
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 12.5.dp, horizontal = 12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            InfoSection(
                                title = getTitleOverride?.invoke(movie) ?: movie.title,
                                genres = movie.genres,
                                paddingTop = 4.dp,
                                rating = movie.rating,
                                showRating = false,
                                modifier = Modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = titleKey),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
                            )

                            DurationAndDateSection(
                                releaseDate = movie.releaseDate?.formatDate() ?: "",
                                modifier = Modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = detailsKey),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
                            )
                        }
                    }

                    if (movie.rating >= 0) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = ratingKey),
                                    animatedVisibilityScope = animatedVisibilityScope,
//                                    boundsTransform = { _, _ ->
//                                        tween(durationMillis = animationTime)
//                                    }
                                ),
                            shape = CircleShape,
                            color = Theme.colors.background.card.copy(alpha = 0.9f),
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "%.1f".format(movie.rating),
                                    color = Theme.colors.shade.primary,
                                    style = Theme.textStyle.label.small.medium
                                )
                                Icon(
                                    painter = painterResource(R.drawable.due_tone_star),
                                    contentDescription = "Rating",
                                    tint = Theme.colors.additional.primary.yellow,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .padding(start = 1.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    else{
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(95.dp)
                .clip(RoundedCornerShape(Theme.radius.large))
                .clickable { onMovieClick(movie.id) },
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(Theme.radius.large),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize()) {
                    SafeImageViewer(
                        imageUrl = movie.posterPath,
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
                            title = getTitleOverride?.invoke(movie) ?: movie.title,
                            genres = movie.genres,
                            paddingTop = 4.dp,
                            rating = movie.rating,
                            showRating = false
                        )

                        DurationAndDateSection(
                            releaseDate = movie.releaseDate?.formatDate() ?: ""
                        )
                    }
                }
                if (movie.rating >= 0) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        shape = CircleShape,
                        color = Theme.colors.background.card.copy(alpha = 0.9f),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "%.1f".format(movie.rating),
                                color = Theme.colors.shade.primary,
                                style = Theme.textStyle.label.small.medium
                            )
                            Icon(
                                painter = painterResource(R.drawable.due_tone_star),
                                contentDescription = "Rating",
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(start = 1.dp)
                            )
                        }
                    }
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