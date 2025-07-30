package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.EmptyState
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cinverse.presentation.R
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ExploreMainContent(
    uiState: ExploreScreenState,
    gridState: LazyGridState,
    contentList: LazyPagingItems<Any>,
    interactionListener: ExploreInteractionListener,
    onVisibilityChange: (searchBarVisible: Boolean, genresVisible: Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var lastScrollOffset by remember { mutableFloatStateOf(0f) }
    var isScrollingDown by remember { mutableStateOf(false) }

    val shouldShowSearchAndGenres by remember {
        derivedStateOf {
            val firstVisibleItemIndex = gridState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = gridState.firstVisibleItemScrollOffset

            when {
                firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset < 50 -> {
                    Pair(true, true)
                }

                isScrollingDown && (firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 100) -> {
                    Pair(false, false)
                }

                !isScrollingDown -> {
                    Pair(true, true)
                }

                else -> {
                    Pair(true, firstVisibleItemScrollOffset < 200)
                }
            }
        }
    }

    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.firstVisibleItemScrollOffset.toFloat() + gridState.firstVisibleItemIndex * 1000f
        }
            .distinctUntilChanged()
            .collect { currentOffset ->
                isScrollingDown = currentOffset > lastScrollOffset
                lastScrollOffset = currentOffset

                val (searchVisible, genresVisible) = shouldShowSearchAndGenres
                onVisibilityChange(searchVisible, genresVisible)
            }
    }

    val gridColumns = remember(uiState.viewMode, uiState.selectedTab) {
        if (uiState.viewMode == ViewMode.GRID) {
            when (uiState.selectedTab) {
                ExploreTabsPages.ACTORS -> GridCells.Fixed(3)
                ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> GridCells.Adaptive(minSize = 160.dp)
            }
        } else {
            GridCells.Fixed(1)
        }
    }

    val contentPadding = remember(uiState.selectedTab) {
        when (uiState.selectedTab) {
            ExploreTabsPages.ACTORS -> PaddingValues(
                top = 16.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 100.dp
            )

            ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> PaddingValues(
                top = 16.dp,
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
                    icon = painterResource(Theme.icons.dueTone.search),
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
                    if (uiState.selectedTab == ExploreTabsPages.ACTORS) 40.dp else 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxSize()
            ) {
                items(contentList.itemCount) { index ->
                    val item = contentList[index]
                    if (item != null) {
                        when (item) {
                            is MediaItemUiState -> {
                                MoviePosterCard(
                                    movie = item,
                                    viewMode = uiState.viewMode,
                                    onMovieClick = { interactionListener.onMediaItemClicked(item) }
                                )
                            }

                            is ExploreScreenState.ActorUiState -> {
                                ActorPosterCard(
                                    actor = item,
                                    viewMode = uiState.viewMode,
                                    onActorClicked = interactionListener::onActorClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}