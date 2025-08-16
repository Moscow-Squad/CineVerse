package com.moscow.cineverse.screen.see_more

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton
import com.moscow.cineverse.screen.see_more.component.ContentGrid
import com.moscow.cineverse.utlis.ViewMode

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
    val pagingItems = viewModel.pagingDataFlow
        .collectAsStateWithLifecycle()
        .value
        .collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            SeeMoreEffectHandler.handleEffect(
                effect = effect,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToSeriesDetails = navigateToSeriesDetails,
                navigateToCastDetails = navigateToCastDetails,
                navigateBack = navigateBack
            )
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

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
private fun <T : Any> SeeMoreContent(
    uiState: SeeMoreUiState,
    gridState: LazyGridState,
    contentList: LazyPagingItems<T>,
    interactionListener: SeeMoreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridColumns = remember(uiState.viewMode) {
        when (uiState.viewMode) {
            ViewMode.GRID -> GridCells.Fixed(2)
            else -> GridCells.Fixed(1)
        }
    }

    MovieScaffold(
        movieAppBar = {
            MovieAppBar(
                title = stringResource(uiState.title),
                showDivider = true,
                showBackButton = true,
                backButtonClick = interactionListener::onNavigateBack
            )
        },
        movieFloatingActionButton = {
            ViewModeToggleButton(
                selectedMode = uiState.viewMode,
                onModeSelected = interactionListener::onViewModeChanged
            )
        }
    ) {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = uiState.viewMode,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = "view_mode_transition"
            ) {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (contentList.loadState.refresh) {
                        is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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

                        is LoadState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                NoInternetScreen(onRetry = interactionListener::onRefresh)
                            }
                        }

                        else -> {
                            ContentGrid(
                                uiState = uiState,
                                gridState = gridState,
                                gridColumns = gridColumns,
                                contentList = contentList,
                                interactionListener = interactionListener,
                                modifier = modifier,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@AnimatedContent
                            )
                        }
                    }
                }
            }
        }
    }
}