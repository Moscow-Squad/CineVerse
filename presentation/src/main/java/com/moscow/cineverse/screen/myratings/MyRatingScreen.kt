package com.moscow.cineverse.screen.myratings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MyRatingScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRatingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToExplore: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val contentList = viewModel.contentList.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshDataIfNeeded()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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

                is MyRatingsEffect.NavigateToExplore -> {
                    navigateToExplore()
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