package com.moscow.cineverse.screen.actor_best_of_movies.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
    enableBlur: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MoviesGrid(
            movies = uiState.movies,
            viewMode = uiState.viewMode,
            onMovieClick = interactionListener::onMovieClick,
            enableBlur = enableBlur,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesGrid(
    movies: List<MediaItemUiState>,
    viewMode: ViewMode,
    enableBlur: String,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout {
        AnimatedContent(
            targetState = viewMode,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "view_mode_transition"
        ) {
            LazyVerticalGrid(
                columns = if (viewMode == ViewMode.GRID) GridCells.Adaptive(minSize = 150.dp) else GridCells.Fixed(
                    1
                ),
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
                        enableBlur = enableBlur,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent
                    )
                }
            }
        }
    }
}