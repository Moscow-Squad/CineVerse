package com.moscow.cineverse.designSystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier,
    movieUI: MovieUI,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    onMovieClick: (Int) -> Unit = {},
    infoModifier: Modifier = Modifier,
    titleTextAlign: TextAlign = TextAlign.Start,
    descriptionTextAlign: TextAlign = TextAlign.Start,
    showGenres: Boolean = false,
    showTitle: Boolean = true
) {
    MovieCard(
        modifier = modifier,
        movieData = movieUI,
        viewMode = viewMode,
        showRating = showRating,
        onMovieClick = onMovieClick,
        infoModifier = infoModifier,
        titleTextAlign = titleTextAlign,
        descriptionTextAlign = descriptionTextAlign,
        showGenres = showGenres,
        showTitle = showTitle,
        getId = { it.id },
        getTitle = { it.title },
        getPosterUrl = { it.posterUrl },
        getRating = { it.rating },
        getGenres = { it.genres },
        getDuration = { it.duration },
        getReleaseDate = { it.releaseDate }
    )
}