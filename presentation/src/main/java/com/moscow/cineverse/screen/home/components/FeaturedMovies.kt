package com.moscow.cineverse.screen.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.MovieListSection
import com.moscow.cineverse.screen.home.HomeFeaturedItems

@Composable
fun FeaturedMovies(
    displayMovies: List<MediaItemUiState>,
    onShowMoreClick: (type: HomeFeaturedItems) -> Unit = {},
    onMovieClick: (MediaItemUiState) -> Unit,
    onSeaMoreRecentlyViewedClicked: () -> Unit = {},
    type: HomeFeaturedItems,
    enableBlur: Boolean,
    modifier: Modifier = Modifier
) {
    MovieListSection(
        title = stringResource(type.titleResource),
        movies = displayMovies,
        paddingHorizontal = 16,
        onClickShowMore = {
            onShowMoreClick(type)
        },
        onClickPoster = { movie ->
            onMovieClick(movie)
        },
        movieCardContent = { movie, cardModifier, onMovieClick ->
            MoviePosterCard(
                movie = movie,
                onMovieClick = { movieId -> onMovieClick(movie) },
                modifier = cardModifier,
                enableBlur = enableBlur,
            )
        },
        modifier = modifier
    )
}
