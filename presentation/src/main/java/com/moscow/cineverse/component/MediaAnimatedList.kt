package com.moscow.cineverse.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.screen.explore.ExploreTabsPages
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaAnimatedList(
    media: LazyPagingItems<MediaItemUiState>,
    onMovieClick: (MediaItemUiState) -> Unit,
    enableBlur: String,
    state: LazyGridState,
    modifier: Modifier = Modifier,
    viewMode: ViewMode = ViewMode.GRID,
    contentPadding: PaddingValues,
    selectedTab: ExploreTabsPages,
    columns: GridCells.Fixed
) {
    SharedTransitionLayout {
        AnimatedContent(
            targetState = viewMode == ViewMode.LIST,
            label = "",
            transitionSpec = {
                (fadeIn(animationSpec = tween(300)) +
                        scaleIn(initialScale = 0.92f, animationSpec = tween(300)))
                    .togetherWith(fadeOut(animationSpec = tween(150)))
            }
        ) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                state = state,
                columns = columns,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(
                    if (selectedTab == ExploreTabsPages.ACTORS) 16.dp else 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    if (selectedTab == ExploreTabsPages.ACTORS) 16.dp else 12.dp
                ),
            ) {
                items(media.itemCount,
                    //key = { index -> media[index]?.id ?: index }
                ) { index ->
                    media[index]?.let { item ->
                        if (it){
                            ListMovieCard(
                                modifier = modifier,
                                movie = item,
                                onMovieClick = { onMovieClick(item) },
                                enableBlur = enableBlur,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                            )
                        }else{
                            GridMovieCard(
                                modifier = modifier,
                                movie = item,
                                onMovieClick = { onMovieClick(item) },
                                enableBlur = enableBlur,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                            )
                        }

                    }
                }
            }
        }
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
    titleTextAlign: TextAlign = TextAlign.Start,
    useFixedHeight: Boolean = false,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Column(
        modifier = modifier
    ) {
        with(sharedTransitionScope) {
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
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "img${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
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
                                .padding(8.dp)
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "rate${movie.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                ),
                            shape = CircleShape,
                            color = Theme.colors.background.card.copy(alpha = 0.9f),
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
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
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "name${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
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
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
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
        with(sharedTransitionScope) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize()) {
                    SafeImageViewer(
                        imageUrl = movie.posterPath,
                        modifier = Modifier
                            .width(64.dp)
                            .fillMaxHeight()
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "img${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
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
                        Column(modifier = modifier) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val title = getTitleOverride?.invoke(movie) ?: movie.title
                                if (title.isNotEmpty()) {
                                    Text(
                                        text = title,
                                        color = Theme.colors.shade.primary,
                                        style = Theme.textStyle.body.medium.medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .weight(1f)
                                            .sharedElement(
                                                sharedContentState = rememberSharedContentState(key = "name${movie.id}"),
                                                animatedVisibilityScope = animatedVisibilityScope
                                            )
                                    )
                                }
                                if (movie.rating > 0) {
                                    Row(
                                        modifier = Modifier.sharedElement(
                                            sharedContentState = rememberSharedContentState(key = "rate${movie.id}"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "%.1f".format(movie.rating),
                                            color = Theme.colors.shade.primary,
                                            style = Theme.textStyle.label.medium.medium,
                                            fontSize = 12.sp
                                        )
                                        Icon(
                                            painter = painterResource(com.moscow.cineverse.design_system.R.drawable.due_tone_star),
                                            contentDescription = "Rating",
                                            tint = Theme.colors.additional.primary.yellow,
                                            modifier = Modifier
                                                .size(22.dp)
                                                .padding(start = 4.dp)
                                        )
                                    }
                                }
                            }
                            if (movie.genres.isNotEmpty()) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .fillMaxWidth()
                                ) {
                                    var genresText = movie.genres.joinToString(", ")
                                    Text(
                                        text = genresText,
                                        style = Theme.textStyle.body.small.regular,
                                        color = Theme.colors.shade.secondary,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        DurationAndDateSection(
                            duration = movie.duration,
                            releaseDate = movie.releaseDate
                        )
                    }
                }
            }
        }
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
private fun DurationAndDateSection(
    modifier: Modifier = Modifier,
    duration: String,
    releaseDate: String
) {
    Row(
        modifier = modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (duration.isNotEmpty()) {
            Icon(
                painter = painterResource(R.drawable.due_tone_clock),
                contentDescription = "Duration",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = duration,
                style = Theme.textStyle.label.medium.medium,
                color = Theme.colors.shade.secondary,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        if (duration.isNotEmpty() && releaseDate.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
        }

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