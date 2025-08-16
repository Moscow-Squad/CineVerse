package com.moscow.cineverse.screen.history

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.component.NoHistoryScreen
import com.moscow.cineverse.component.SwipeToDelete
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.card.InfoCard
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (movieId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
    navigateToExploreScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HistoryEffect.MovieClicked -> navigateToMovieDetails(effect.movieId)
                HistoryEffect.NavigateBack -> navigateBack()
                is HistoryEffect.SeriesClicked -> navigateToSeriesDetails(effect.seriesId)
                HistoryEffect.WatchSomethingButtonClicked -> navigateToExploreScreen()
            }
        }
    }

    HistoryContent(state = state, interactionListener = viewModel)
}

@Composable
fun HistoryContent(
    state: HistoryScreenState,
    interactionListener: HistoryInteractionListener,
    modifier: Modifier = Modifier,
) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                backButtonClick = interactionListener::onBackPressed ,
                title = stringResource(R.string.history)
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        MovieCircularProgressBar(
                            gradientColors = listOf(
                                Theme.colors.brand.primary,
                                Theme.colors.brand.tertiary
                            )
                        )
                    }
                }
                state.errorMessage != null -> {
                    ErrorContent(
                        onRetry = interactionListener::onRetry,
                        errorMessage = state.errorMessage
                    )
                }
                state.isContentEmpty -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        NoHistoryScreen(onContinue = interactionListener::onFindToSomethingToWatchButton )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .background(Theme.colors.background.screen),
                    ) {
                        if (state.showTip && state.youRecentlyViewed.isNotEmpty()) {
                            item {
                                InfoCard(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .padding(bottom = 24.dp),
                                    text = stringResource(R.string.tip_swipe_left_to_remove_movies_from_your_history),
                                    onDismiss = interactionListener::onTipCancelIconClicked
                                )
                            }
                        }
                        items(
                            items = state.youRecentlyViewed,
                            key = { it.id }
                        ) { item ->
                            SwipeToDelete(
                                modifier = Modifier
                                    .animateItem()
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                onDelete = { interactionListener.onItemDeletedIconClicked(item.id) }
                            ) {
                                HistoryMediaItemCard(
                                    movie = item,
                                    onMediaItemClick = { interactionListener.onMediaItemClicked(item) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HistoryMediaItemCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    onMediaItemClick: (Int) -> Unit = {},
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
) {
    MediaPosterCard(
        modifier = modifier,
        mediaItem = movie,
        onMediaItemClick = onMediaItemClick,
        getTitleOverride = getTitleOverride,
        viewMode = ViewMode.LIST,
        enableBlur = "high"
    )
}