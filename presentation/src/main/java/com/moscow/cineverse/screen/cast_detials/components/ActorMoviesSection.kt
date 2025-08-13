package com.moscow.cineverse.screen.cast_detials.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.component.MovieListSection
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.mapper.toMovieItemUi
import com.moscow.cineverse.screen.cast_detials.CastDetailsInteractionListener
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun ActorMoviesSection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.movies.isNotEmpty()) {
        MovieListSection(
            title = stringResource(
                R.string.best_of,
                uiState.actor?.name.orEmpty()
            ),
            movies = uiState.movies.take(10),
            onClickShowMore = interactionListener::onShowMoreMovies,
            onClickPoster = interactionListener::onMovieClick,
            movieCardContent = { movie, cardModifier, onMovieClick ->
                MoviePosterCard(
                    movie = movie.toMovieItemUi(),
                    viewMode = ViewMode.GRID,
                    showRating = true,
                    enableBlur = uiState.enableBlur,
                    onMovieClick = { onMovieClick(movie) },
                    showTitle = true,
                    modifier = cardModifier
                )
            },
            modifier = modifier
        )
    }
}

