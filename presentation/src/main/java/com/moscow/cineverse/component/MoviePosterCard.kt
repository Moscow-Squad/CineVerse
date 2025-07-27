package com.moscow.cineverse.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.MovieCard
import com.moscow.cineverse.designSystem.component.ViewMode

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMovieClick: (Int) -> Unit = {},
    titleTextAlign: TextAlign = TextAlign.Start,
    showTitle: Boolean = true,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
) {
    MovieCard(
        modifier = modifier,
        movieData = movie,
        viewMode = viewMode,
        showRating = showRating,
        onMovieClick = onMovieClick,
        titleTextAlign = titleTextAlign,
        showTitle = showTitle,
        showBackdrop = showBackdrop,
        getId = { it.id },
        getTitle = { getTitleOverride?.invoke(it) ?: it.title },
        getPosterUrl = { it.posterPath },
        getBackdropUrl = { it.backdropPath },
        getRating = { it.rating },
        getGenres = { it.genres },
        getDuration = { it.duration },
        getReleaseDate = { it.releaseDate }
    )
}