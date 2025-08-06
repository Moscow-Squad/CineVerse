package com.moscow.cineverse.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.ListMovieCard
import com.moscow.cinverse.presentation.R

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsStateWithLifecycle().value.collectAsLazyPagingItems()
    HistoryContent(interactionListener = viewModel, historyPagingItems = pagingItems )
}

@Composable
fun HistoryContent(
    interactionListener: HistoryInteractionListener,
    historyPagingItems: LazyPagingItems<MediaItemUiState>,
    modifier: Modifier = Modifier,
    ) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                backButtonClick = { interactionListener.onBackPressed() },
                title = stringResource(R.string.top_reviews)
            )
        }
    ){
        Column(modifier = modifier) {

            when (historyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        MovieCircularProgressBar(
                            gradientColors = listOf(
                                Theme.colors.brand.primary,
                                Theme.colors.brand.tertiary
                            )
                        )
                    }
                }

                is LoadState.Error -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        NoInternetScreen(onRetry = interactionListener::onRefresh)
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(historyPagingItems.itemCount) { index ->
                            val item = historyPagingItems[index]
                            if (item != null) {
                                HistoryMovieCard(
                                    movie = item
                                )
                            }
                        }

                        if (historyPagingItems.loadState.append is LoadState.Loading && historyPagingItems.itemCount > 20) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MovieCircularProgressBar(
                                        gradientColors = listOf(
                                            Theme.colors.brand.primary,
                                            Theme.colors.brand.tertiary
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryMovieCard(
    modifier: Modifier = Modifier,
    movie: MediaItemUiState,
    onMovieClick: (Int) -> Unit = {},
    getTitleOverride: ((MediaItemUiState) -> String)? = null,
) {
    ListMovieCard(
        modifier = modifier,
        movieData = movie,
        onMovieClick = onMovieClick,
        getId = { it.id },
        getTitle = { getTitleOverride?.invoke(it) ?: it.title },
        getPosterUrl = { it.posterPath },
        getRating = { it.rating },
        getGenres = { it.genres },
        getDuration = { it.duration },
        getReleaseDate = { it.releaseDate }
    )
}