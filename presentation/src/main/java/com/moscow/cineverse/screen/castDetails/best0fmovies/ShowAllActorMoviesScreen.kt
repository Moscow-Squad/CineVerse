package com.moscow.cineverse.screen.castDetails.best0fmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.screen.castDetails.best0fmovies.component.ShowAllActorMoviesContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ShowAllActorMoviesScreen(
    actorId: Int,
    title: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = LocalNavController.current,
) {
    val viewModel: ShowAllActorMoviesInteractionViewModel = koinViewModel(
        parameters = { parametersOf(actorId, title) }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ShowAllActorMoviesEffect.NavigateMovieDetails -> {
                    navController.navigate(MovieDetailsRoute(effect.movieId))
                }
            }
        }
    }

    ShowAllActorMoviesContent(
        uiState = uiState,
        interactionListener = viewModel,
        modifier = modifier,
        title = title,
        onNavigateBack = { navController.popBackStack() },
    )
}