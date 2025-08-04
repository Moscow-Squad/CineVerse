package com.moscow.cineverse.screen.myratings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MyRatingScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRatingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val contentList = viewModel.contentList.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MyRatingsEffect.MovieClicked -> {
                    navigateToMovieDetails(effect.movieId)
                }

                is MyRatingsEffect.SeriesClicked -> {
                    navigateToSeriesDetails(effect.seriesId)
                }
                is MyRatingsEffect.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }

    MyRatingsContent(
        uiState = state,
        contentList = contentList,
        interactionListener = viewModel,
        modifier = modifier
    )
}