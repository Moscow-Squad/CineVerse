package com.moscow.cineverse.screen.explore.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moscow.cineverse.screen.MovieCard
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun ActorPosterCard(
    actor: ExploreScreenState.ActorUiState,
    modifier: Modifier = Modifier,
    viewMode: ViewMode = ViewMode.GRID,
    titleTextAlign: TextAlign = TextAlign.Center,
    onActorClicked: (Int) -> Unit = {}
) {
    MovieCard(
        modifier = modifier,
        movieData = actor,
        viewMode = viewMode,
        titleTextAlign = titleTextAlign,
        showRating = false,
        showTitle = true, 
        getId = { it.id },
        getTitle = { it.title },
        getPosterUrl = { it.profilePath },
        getRating = { 0.0f },
        getGenres = { listOf() },
        getDuration = { "" },
        getReleaseDate = {""},
        onMovieClick = onActorClicked
    )
}