package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
@Composable
fun ExploreMainContent(
    uiState: ExploreScreenState,
    gridState: LazyGridState,
    contentList: LazyPagingItems<Any>,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridColumns = remember(uiState.viewMode, uiState.selectedTab) {
        when (uiState.selectedTab) {
            ExploreTabsPages.ACTORS -> GridCells.Fixed(3)
            ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES ->
                if (uiState.viewMode == ViewMode.GRID)
                    GridCells.Fixed(2)
                else
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

    val spacing = remember(uiState.selectedTab) {
        when (uiState.selectedTab) {
            ExploreTabsPages.ACTORS -> 16.dp
            ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> 16.dp
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
                verticalArrangement = Arrangement.spacedBy(if (uiState.selectedTab == ExploreTabsPages.ACTORS) 40.dp else 16.dp),
                horizontalArrangement = Arrangement.spacedBy(if (uiState.selectedTab == ExploreTabsPages.ACTORS) 16.dp else 12.dp),
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
                                    onActorClicked = interactionListener::onActorClick,
                                    modifier = Modifier.aspectRatio(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}