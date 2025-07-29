package com.moscow.cineverse.screen.see_more

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.navigation.routes.CastDetailsRoute
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute

@Composable
fun SeeMoreHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SeeMoreViewModel = hiltViewModel(),
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val pagingItems = viewModel.pagingDataFlow.collectAsStateWithLifecycle().value.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SeeMoreEvent.MovieClicked -> {
                    navigateToMovieDetails(effect.movieId)
                }
                is SeeMoreEvent.SeriesClicked -> {
                    navigateToSeriesDetails(effect.seriesId)
                }
                is SeeMoreEvent.ActorClicked -> {
                    navigateToCastDetails(effect.actorId)
                }
                is SeeMoreEvent.NavigateBack -> {
                    navigateBack()
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
