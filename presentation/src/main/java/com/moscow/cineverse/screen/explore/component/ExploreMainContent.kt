package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.EmptyState
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cineverse.screen.explore.ExploreTabsPages
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExploreMainContent(
    uiState: ExploreScreenState,
    gridState: LazyGridState,
    contentList: LazyPagingItems<Any>,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier,
    onGenresVisibilityChange: (Boolean) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    var lastScrollOffset by remember { mutableFloatStateOf(0f) }
    var isScrollingDown by remember { mutableStateOf(false) }

    val shouldShowGenres by remember {
        derivedStateOf {
            val firstVisibleItemIndex = gridState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = gridState.firstVisibleItemScrollOffset

            if (firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset < 100) {
                true
            } else if (isScrollingDown) {
                false
            } else {
                true
            }
        }
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.firstVisibleItemScrollOffset.toFloat() + gridState.firstVisibleItemIndex * 1000f }
            .distinctUntilChanged()
            .collect { currentOffset ->
                isScrollingDown = currentOffset > lastScrollOffset
                lastScrollOffset = currentOffset
                onGenresVisibilityChange(shouldShowGenres && uiState.shouldShowGenres)
            }
    }

    val gridColumns = remember(uiState.viewMode, uiState.selectedTab) {
        when (uiState.selectedTab) {
            ExploreTabsPages.ACTORS -> GridCells.Fixed(3)
            ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES ->
                if (uiState.viewMode == ViewMode.GRID)
                    GridCells.Adaptive(minSize = 150.dp)
                else
                    GridCells.Fixed(1)
        }
    }

    val contentPadding = remember(uiState.selectedTab) {
        when (uiState.selectedTab) {
            ExploreTabsPages.ACTORS -> PaddingValues(
                top = if (uiState.isSearch) 16.dp else 64.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 100.dp
            )

            ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> PaddingValues(
                top = if (uiState.isSearch) 16.dp else 64.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 100.dp
            )
        }
    }

    when {
        uiState.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MovieCircularProgressBar(
                    gradientColors = listOf(
                        Theme.colors.brand.primary,
                        Theme.colors.brand.tertiary
                    )
                )
            }
        }

        uiState.shouldShowError -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                NoInternetScreen(onRetry = interactionListener::onRefresh)
            }
        }

        uiState.isContentEmpty -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon = painterResource(R.drawable.ic_search),
                    title = stringResource(R.string.nothing_found),
                    description = stringResource(R.string.we_searched_the_entire_universe_but_found_nothing_matching_your_query_try_something_else)
                )
            }
        }

        else -> {
            LazyVerticalGrid(
                state = gridState,
                columns = gridColumns,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(
                    if (uiState.selectedTab == ExploreTabsPages.ACTORS) 16.dp else 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    if (uiState.selectedTab == ExploreTabsPages.ACTORS) 16.dp else 12.dp
                ),
                modifier = modifier.fillMaxSize()
            ) {
                items(contentList.itemCount) { index ->
                    val item = contentList[index]
                    if (item != null) {
                        when (item) {
                            is MediaItemUiState -> {
                                MediaPosterCard(
                                    mediaItem = item,
                                    viewMode = uiState.viewMode,
                                    enableBlur = uiState.enableBlur,
                                    onMediaItemClick = { interactionListener.onMediaItemClicked(item) },
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            }

                            is ExploreScreenState.ActorUiState -> {
                                ActorPosterCard(
                                    actor = item,
                                    enableBlur = uiState.enableBlur,
                                    onActorClicked = interactionListener::onActorClick,
                                )
                            }
                        }
                    }
                }
                if (contentList.loadState.append is LoadState.Loading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier.height(214.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCircularProgressBar()
                        }
                    }
                }
                if (contentList.loadState.append is LoadState.Error) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NoInternetScreen(onRetry = { contentList.retry() })
                    }
                }
            }
        }
    }
}