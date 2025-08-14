package com.moscow.cineverse.screen.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.MovieListSection
import com.moscow.cineverse.screen.home.HomeFeaturedItems

@Composable
fun FeaturedMovies(
    modifier: Modifier = Modifier,
    displayMovies: List<MediaItemUiState>,
    onShowMoreClick: ((type: HomeFeaturedItems) -> Unit)? = null,
    onMovieClick: (MediaItemUiState) -> Unit,
    onSeaMoreRecentlyViewedClicked: () -> Unit = {},
    type: HomeFeaturedItems,
    enableBlur: String,
) {
    MovieListSection(
        title = stringResource(type.titleResource),
        mediaItems = displayMovies,
        paddingHorizontal = 16,
        onClickShowMore = {
            if (onShowMoreClick != null)
                onShowMoreClick(type)
            else
                onSeaMoreRecentlyViewedClicked()
        },
        onClickPoster = { movie ->
            onMovieClick(movie)
        },
        movieCardContent = { movie, cardModifier, onMovieClick ->
            MediaPosterCard(
                mediaItem = movie,
                onMediaItemClick = { movieId -> onMovieClick(movie) },
                modifier = cardModifier,
                enableBlur = enableBlur,
            )
        },
        modifier = modifier
    )
}
