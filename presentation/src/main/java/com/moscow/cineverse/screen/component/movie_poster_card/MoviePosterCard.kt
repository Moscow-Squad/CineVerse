package com.moscow.cineverse.screen.component.movie_poster_card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moscow.cineverse.designSystem.component.MovieCard
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.screen.model.MediaItemUi

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUi,
    viewMode: ViewMode = ViewMode.GRID,
    showRating: Boolean = true,
    onMovieClick: (Int) -> Unit = {},
    titleTextAlign: TextAlign = TextAlign.Start,
    descriptionTextAlign: TextAlign = TextAlign.Start,
    showGenres: Boolean = false,
    showTitle: Boolean = true,
    getTitleOverride: ((MediaItemUi) -> String)? = null,
) {
    MovieCard(
        modifier = modifier,
        movieData = movie,
        viewMode = viewMode,
        showRating = showRating,
        onMovieClick = onMovieClick,
        titleTextAlign = titleTextAlign,
        showTitle = showTitle,
        getId = { it.id },
        getTitle = { it.title },
        getPosterUrl = { it.posterPath },
        getRating = { it.rating },
        getGenres = { it.genres },
        getDuration = { it.duration },
        getReleaseDate = { it.releaseDate }
    )
}