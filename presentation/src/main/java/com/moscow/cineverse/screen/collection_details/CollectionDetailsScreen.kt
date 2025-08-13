package com.moscow.cineverse.screen.collection_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.paging.compose.itemKey
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.EmptyCollection
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.component.SwipeToDelete
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.card.InfoCard
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun CollectionDetailsScreen(
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToExplore: () -> Unit,
    viewModel: CollectionDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val mediaItems = uiState.movies.collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                CollectionDetailsEffect.NavigateBack -> {
                    navigateBack()
                }

                is CollectionDetailsEffect.NavigateToMovieDetails -> {
                    navigateToMovieDetails(event.movieId)
                }

                is CollectionDetailsEffect.NavigateToSeriesDetails -> {
                    navigateToSeriesDetails(event.seriesId)
                }
                is CollectionDetailsEffect.OnStartCollecting -> navigateToExplore()
            }
        }
    }
    CollectionDetailsScreenContent(
        title = viewModel.collectionName,
        uiState = uiState,
        mediaItems = mediaItems,
        interactionListener = viewModel,
        navigateBack = navigateBack
    )
}

@Composable
private fun CollectionDetailsScreenContent(
    title: String,
    uiState: CollectionDetailsScreenState,
    mediaItems: LazyPagingItems<MediaItemUiState>,
    interactionListener: CollectionDetailsInteractionListener,
    navigateBack: () -> Unit,
) {
    if (uiState.isLoading && mediaItems.itemCount == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen),
            contentAlignment = Alignment.Center
        ) {
            MovieCircularProgressBar()
        }
    } else if (uiState.isError) {
        ErrorContent(
            errorMessage = uiState.errorMsg,
            onRetry = interactionListener::onRefresh,
        )
    } else {
        if (mediaItems.loadState.refresh is LoadState.Loading && mediaItems.itemCount == 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentAlignment = Alignment.Center
            ) {
                MovieCircularProgressBar()
            }
        } else if (mediaItems.loadState.refresh is LoadState.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentAlignment = Alignment.Center
            ) {
                NoInternetScreen(onRetry = { mediaItems.retry() })
            }
        } else {
            MovieScaffold(
                movieAppBar = {
                    MovieAppBar(
                        backButtonClick = navigateBack,
                        title = title,
                    )
                }
            ) {
                if (mediaItems.itemCount == 0) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyCollection(onStartCollectingClick = interactionListener::onStartCollectingClick)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        if (uiState.showTip) {
                            item {
                                InfoCard(
                                    modifier = Modifier.padding(bottom = 24.dp),
                                    text = stringResource(R.string.tip_swipe_left_to_remove_movies_from_your_collection),
                                    onDismiss = interactionListener::onTipCancelIconClicked
                                )
                            }
                        }
                        items(
                            count = mediaItems.itemCount,
                            key = mediaItems.itemKey { it.id }
                        ) { index ->
                            val media = mediaItems[index] ?: return@items
                            SwipeToDelete(
                                modifier = Modifier
                                    .animateItem()
                                    .padding(bottom = 16.dp),
                                onDelete = {
                                    interactionListener.onItemDeletedIconClicked(
                                        mediaId = media.id,
                                        mediaType = media.mediaType
                                    )
                                }
                            ) {
                                MoviePosterCard(
                                    movie = media,
                                    viewMode = ViewMode.LIST,
                                    showRating = true,
                                    onMovieClick = {
                                        interactionListener.onMediaItemClicked(
                                            mediaId = media.id,
                                            mediaType = media.mediaType
                                        )
                                    },
                                    showTitle = true,
                                    showBackdrop = true,
                                    enableBlur = uiState.enableBlur
                                )
                            }
                        }
                        if (mediaItems.loadState.append is LoadState.Loading && mediaItems.itemCount > 20) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(214.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MovieCircularProgressBar()
                                }
                            }
                        }
                        if (mediaItems.loadState.append is LoadState.Error) {
                            item {
                                NoInternetScreen(
                                    onRetry = { mediaItems.retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}