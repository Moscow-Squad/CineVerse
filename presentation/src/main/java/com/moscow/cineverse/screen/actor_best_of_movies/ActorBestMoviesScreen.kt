package com.moscow.cineverse.screen.actor_best_of_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.actor_best_of_movies.component.LoadingContent
import com.moscow.cineverse.screen.actor_best_of_movies.component.SuccessContent
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton

@Composable
fun ActorBestMoviesScreen(
    modifier: Modifier = Modifier,
    navigateMovieDetails: (Int) -> Unit,
    navigateBack: () -> Unit = {},
    viewModel: ActorBestMoviesViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            ActorBestMoviesEffectHandler.handleEffect(
                effect,
                navigateMovieDetails = navigateMovieDetails,
                navigateBack = navigateBack

            )
        }
    }

   ActorBestMoviesContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        onNavigateBack = navigateBack,
    )
}

@Composable
private fun ActorBestMoviesContent(
    uiState: ActorBestMoviesState,
    interactionListener: ActorBestMoviesInteractionListener,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MovieScaffold {
        Box(modifier = modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    LoadingContent(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    ErrorContent(
                        errorMessage = uiState.error,
                        onRetry = interactionListener::onRefresh,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    SuccessContent(
                        uiState = uiState,
                        interactionListener = interactionListener,
                        title = uiState.actorName,
                        enableBlur = uiState.enableBlur,
                        onNavigateBack = onNavigateBack
                    )

                    ViewModeToggleButton(
                        selectedMode = uiState.viewMode,
                        onModeSelected = interactionListener::onViewModeChanged,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
