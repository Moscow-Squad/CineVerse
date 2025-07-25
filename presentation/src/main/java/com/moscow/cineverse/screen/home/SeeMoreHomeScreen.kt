package com.moscow.cineverse.screen.home

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.navigation.LocalNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeMoreHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SeeMoreHomeViewModel = koinViewModel(),
    navController: NavHostController = LocalNavController.current
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val pagingItems = viewModel.pagingDataFlow.collectAsStateWithLifecycle().value.collectAsLazyPagingItems()

    // Handle navigation events
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SeeMoreHomeEvent.MovieClicked -> {
                    navController.navigate("movie_details/${effect.movieId}")
                }
                is SeeMoreHomeEvent.SeriesClicked -> {
                    navController.navigate("series_details/${effect.seriesId}")
                }
                is SeeMoreHomeEvent.ActorClicked -> {
                    navController.navigate("cast_details/${effect.actorId}")
                }
                is SeeMoreHomeEvent.NavigateBack -> {
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
