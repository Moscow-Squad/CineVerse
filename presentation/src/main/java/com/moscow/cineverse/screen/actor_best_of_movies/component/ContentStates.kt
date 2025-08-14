package com.moscow.cineverse.screen.actor_best_of_movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.actor_best_of_movies.ActorBestMoviesInteractionListener
import com.moscow.cineverse.screen.actor_best_of_movies.ActorBestMoviesState
import com.moscow.cineverse.utlis.ViewMode

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
) {
    MovieCircularProgressBar(
        modifier = modifier,
        gradientColors = listOf(
            Theme.colors.brand.primary,
            Theme.colors.brand.tertiary
        )
    )
}

@Composable
fun SuccessContent(
    uiState: ActorBestMoviesState,
    interactionListener: ActorBestMoviesInteractionListener,
    title: String,
    enableBlur: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MovieAppBar(
            title = title,
            backButtonClick = onNavigateBack,
        )

        MoviesGrid(
            movies = uiState.movies,
            viewMode = uiState.viewMode,
            onMovieClick = interactionListener::onMovieClick,
            enableBlur = enableBlur,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MoviesGrid(
    movies: List<MediaItemUiState>,
    viewMode: ViewMode,
    enableBlur: String,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = if (viewMode == ViewMode.GRID) GridCells.Fixed(2) else GridCells.Fixed(1),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MediaPosterCard(
                mediaItem = movie,
                viewMode = viewMode,
                onMediaItemClick = onMovieClick,
                enableBlur = enableBlur
            )
        }
    }
}