package com.moscow.cineverse.screen.cast_details.best_of_movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.cast_details.best_of_movies.ShowAllActorMoviesInteractionListener
import com.moscow.cineverse.screen.cast_details.best_of_movies.ShowAllActorMoviesState
import com.moscow.cinverse.presentation.R

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
    uiState: ShowAllActorMoviesState,
    interactionListener: ShowAllActorMoviesInteractionListener,
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
    movies: List<com.moscow.cineverse.common_ui_state.MediaItemUiState>,
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
            MoviePosterCard(
                movie = movie,
                viewMode = viewMode,
                onMovieClick = onMovieClick,
                enableBlur = enableBlur
            )
        }
    }
}