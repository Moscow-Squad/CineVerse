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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.component.SwipeToDelete
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.message_info.InfoCard
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.MovieCard
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R
import com.moscow.domain.model.Movie

@Composable
fun CollectionDetailsScreen(
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
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
    mediaItems: LazyPagingItems<Movie>,
    interactionListener: CollectionDetailsInteractionListener,
    navigateBack: () -> Unit,
) {
    if (uiState.isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen),
            contentAlignment = Alignment.Center
        ) {
            MovieCircularProgressBar()
        }
    }
    else if(uiState.isError){
        ErrorContent(
            errorMessage = uiState.errorMsg,
            onRetry = interactionListener::onRefresh,
        )
    }else{
        if (mediaItems.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentAlignment = Alignment.Center
            ) {
                MovieCircularProgressBar()
            }
        }
        else if (mediaItems.loadState.refresh is LoadState.Error) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen),
                contentAlignment = Alignment.Center
            ) {
                NoInternetScreen(onRetry = { mediaItems.retry() })
            }
        }
        else {
            MovieScaffold(
                movieAppBar = {
                    MovieAppBar(
                        backButtonClick = navigateBack,
                        title = title,
                    )
                }
            ) {
                LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    if (uiState.showTip){
                        item {
                            InfoCard(
                                modifier = Modifier.padding(bottom = 24.dp),
                                text = stringResource(R.string.tip_swipe_left_to_remove_movies_from_your_collection),
                                onDismiss = interactionListener::onTipCancelIconClicked
                            )
                        }
                    }
                    items(mediaItems.itemCount) { index ->
                        val media = mediaItems[index]?.toUi(uiState.moviesGenres)
                        if (media != null) {
                            SwipeToDelete(
                                modifier = Modifier.padding(bottom = 16.dp),
                                onDelete = {
                                    interactionListener.onItemDeletedIconClicked(
                                        mediaId = media.id,
                                        mediaType = media.mediaType
                                    )
                                }
                            ) {
                                MovieCard(
                                    movieData = media,
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
                                    getId = { media.id },
                                    getTitle = { media.title },
                                    getPosterUrl = { media.posterPath },
                                    getBackdropUrl = { media.backdropPath },
                                    getRating = { media.rating },
                                    getGenres = { media.genres },
                                    getDuration = { media.duration },
                                    getReleaseDate = { media.releaseDate },
                                    enableBlur = uiState.enableBlur,
                                )
                            }
                        }
                    }
                    if (mediaItems.loadState.append is LoadState.Loading) {
                        item{
                            Box(
                                modifier = Modifier.fillMaxWidth().height(214.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                MovieCircularProgressBar()
                            }
                        }
                    }
                    if (mediaItems.loadState.append is LoadState.Error) {
                        item{
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