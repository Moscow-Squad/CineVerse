package com.moscow.cineverse.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.MovieCard
import com.moscow.cineverse.utlis.ViewMode

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    showBackdrop: Boolean = false,
    onMovieClick: (Int) -> Unit = {},
    enableBlur: Boolean,
    titleTextAlign: TextAlign = TextAlign.Start,
    showTitle: Boolean = true,
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
) {
    Log.d("blur-", "${enableBlur}")

    MovieCard(
        modifier = modifier,
        movieData = movie,
        viewMode = viewMode,
        showRating = showRating,
        enableBlur = enableBlur,
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