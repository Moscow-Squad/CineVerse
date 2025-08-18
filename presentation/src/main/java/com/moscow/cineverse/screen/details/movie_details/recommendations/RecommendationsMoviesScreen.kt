package com.moscow.cineverse.screen.details.movie_details.recommendations

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.ErrorContent
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R

@Composable
fun RecommendationMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: RecommendationsMoviesViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recommendations = uiState.recommendation.collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { event ->
            when(event){
                RecommendationMoviesEffect.NavigateBack -> {
                    navigateBack()
                }

                is RecommendationMoviesEffect.MovieClicked -> {
                    navigateToMovieDetails(
                        event.movieId,
                    )
                }
            }
        }
    }
    RecommendationMoviesContent(
        uiState,
        recommendations = recommendations,
        interactionListener = viewModel,
        modifier = modifier,
        title = uiState.movieTitle
    )
}


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecommendationMoviesContent(
    uiState: RecommendationsMoviesState,
    recommendations: LazyPagingItems<MediaItemUiState>,
    interactionListener: RecommendationsMoviesInteractionListener,
    modifier: Modifier = Modifier,
    title: String
) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                caption = stringResource(R.string.because_you_watched),
                title = title,
                backButtonClick = interactionListener::backButtonClick,
            )
        }
    ) {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = uiState.viewMode,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "view_mode_transition"
            ) {
                Box(modifier = modifier.fillMaxSize()) {
                    if (uiState.isLoading){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCircularProgressBar()
                        }
                    }
                    else if (uiState.error != null){
                        ErrorContent(
                            errorMessage = uiState.error,
                            onRetry = {
                                interactionListener.onRetry()
                                recommendations.retry()
                            },
                        )
                    }
                    else{
                        if (recommendations.loadState.refresh is LoadState.Loading){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                MovieCircularProgressBar()
                            }
                        }
                        else if(recommendations.loadState.refresh is LoadState.Error){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                NoInternetScreen(onRetry = { recommendations.retry() })
                            }
                        }
                        else{
                            Column(modifier = Modifier.fillMaxSize()) {
                                LazyVerticalGrid(
                                    columns = if (uiState.viewMode == ViewMode.GRID)
                                        GridCells.Adaptive(minSize = 150.dp)
                                    else
                                        GridCells.Fixed(1),
                                    contentPadding = PaddingValues(
                                        vertical = 16.dp,
                                        horizontal = 16.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(recommendations.itemCount)
                                    { index ->
                                        val recommendation = recommendations[index]
                                        if (recommendation != null) {
                                            MediaPosterCard(
                                                mediaItem = recommendation,
                                                viewMode = uiState.viewMode,
                                                onMediaItemClick = interactionListener::onMovieClick,
                                                enableBlur = uiState.enableBlur,
                                                sharedTransitionScope = this@SharedTransitionLayout,
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                        }
                                    }
                                    if (recommendations.loadState.append is LoadState.Loading) {
                                        item(span = {GridItemSpan(maxLineSpan)}){
                                            Box(
                                                modifier = Modifier.height(214.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                MovieCircularProgressBar(Modifier)
                                            }
                                        }
                                    }
                                    if (recommendations.loadState.append is LoadState.Error) {
                                        item(span = { GridItemSpan(maxLineSpan) }) {
                                            NoInternetScreen(
                                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                                onRetry = { recommendations.retry() }
                                            )
                                        }
                                    }
                                }
                            }
                            ViewModeToggleButton(
                                selectedMode = uiState.viewMode,
                                onModeSelected = interactionListener::onViewModeChanged,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}