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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.message_info.InfoCard
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.ListMovieCard
import com.moscow.cineverse.screen.explore.component.EmptyState
import com.moscow.cinverse.presentation.R

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (movieId: Int) -> Unit,
    navigateToSeriesDetails: (seriesId: Int) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is HistoryEffect.MovieClicked -> navigateToMovieDetails
                HistoryEffect.NavigateBack -> navigateBack
                is HistoryEffect.SeriesClicked -> navigateToSeriesDetails
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
                backButtonClick = { interactionListener.onBackPressed() },
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
                        EmptyState(
                            icon = painterResource(Theme.icons.dueTone.search),
                            title = stringResource(R.string.nothing_found),
                            description = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else)
                        )
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
                        if (state.showTip && state.youRecentlyViewed.isNotEmpty()){
                            item {
                                InfoCard(
                                    modifier = Modifier.padding(bottom = 24.dp),
                                    text = stringResource(R.string.tip_swipe_left_to_remove_movies_from_your_collection),
                                    onDismiss = interactionListener::onTipCancelIconClicked
                                )
                            }
                        }

                        items(state.youRecentlyViewed) { item ->
                            HistoryMediaItemCard(
                                movie = item,
                                onMediaItemClick = { interactionListener.onMediaItemClicked(item) })
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
        getReleaseDate = { it.releaseDate }
    )
}