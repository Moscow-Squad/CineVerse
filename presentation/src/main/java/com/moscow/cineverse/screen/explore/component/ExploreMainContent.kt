package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridColumns = remember(uiState.viewMode, uiState.selectedTab) {
        if (uiState.viewMode == ViewMode.GRID) {
            when (uiState.selectedTab) {
                ExploreTabsPages.ACTORS -> GridCells.Adaptive(minSize = 98.dp)
                ExploreTabsPages.MOVIES, ExploreTabsPages.SERIES -> GridCells.Adaptive(minSize = 160.dp)
            }
        } else {
            GridCells.Fixed(1)
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
                contentPadding = PaddingValues(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.fillMaxSize()
            ) {
                items(uiState.contentList) { item ->
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
