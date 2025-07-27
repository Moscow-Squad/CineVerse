package com.moscow.cineverse.screen.see_more

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.navigation.LocalNavController
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeMoreHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SeeMoreViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val pagingItems = viewModel.pagingDataFlow.collectAsStateWithLifecycle().value.collectAsLazyPagingItems()

    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SeeMoreEvent.MovieClicked -> {
                    navController.navigate(MovieDetailsRoute(effect.movieId))
                }
                is SeeMoreEvent.SeriesClicked -> {
                    navController.navigate(SeriesDetailsRoute(effect.seriesId))
                }
                is SeeMoreEvent.ActorClicked -> {
                    navController.navigate(CastDetailsRoute(effect.actorId))
                }
                is SeeMoreEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    SeeMoreContent(
        uiState = state,
        gridState = gridState,
        contentList = pagingItems,
        interactionListener = viewModel,
        modifier = modifier
    )
}
