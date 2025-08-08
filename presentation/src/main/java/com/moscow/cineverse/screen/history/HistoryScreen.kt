package com.moscow.cineverse.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import com.moscow.cineverse.component.HistoryTip
import com.moscow.cineverse.component.NoHistoryScreen
import com.moscow.cineverse.component.SwipeToDelete
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.ListMovieCard
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
                is HistoryEffect.MovieClicked -> navigateToMovieDetails
                HistoryEffect.NavigateBack -> navigateBack
                is HistoryEffect.SeriesClicked -> navigateToSeriesDetails
                HistoryEffect.WatchSomethingButtonClicked -> navigateToExploreScreen
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
                backButtonClick =  interactionListener::onBackPressed ,
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

                state.isContentEmpty -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        NoHistoryScreen(onContinue = interactionListener::onFindToSomethingToWatchButton )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = modifier
                            .fillMaxSize()
                            .background(Theme.colors.background.screen),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        if (state.showTip && state.youRecentlyViewed.isNotEmpty()) {
                            item {
                                HistoryTip(
                                    modifier = Modifier.padding(bottom = 24.dp),
                                    onDismiss = interactionListener::onTipCancelIconClicked
                                )
                            }
                        }

                        items(state.youRecentlyViewed) { item ->
                            SwipeToDelete(
                                modifier = Modifier.padding(bottom = 16.dp),
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

@Composable
fun HistoryMediaItemCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    onMediaItemClick: (Int) -> Unit = {},
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
) {
    ListMovieCard(
        modifier = modifier,
        movieData = movie,
        onMovieClick = onMediaItemClick,
        getId = { it.id },
        getTitle = { getTitleOverride?.invoke(it) ?: it.title },
        getPosterUrl = { it.posterPath },
        getRating = { it.rating },
        getGenres = { it.genres },
        getDuration = { it.duration },
        getReleaseDate = { it.releaseDate },
        enableBlur = "high"
    )
}