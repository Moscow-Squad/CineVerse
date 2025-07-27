package com.moscow.cineverse.screen.see_more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.EmptyState
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.component.MovieScaffold
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.component.ViewModeToggleButton
import com.moscow.cinverse.presentation.R

@Composable
fun <T : Any> SeeMoreContent(
    uiState: SeeMoreUiState,
    gridState: LazyGridState,
    contentList: LazyPagingItems<T>,
    interactionListener: SeeMoreInteractionListener,
    modifier: Modifier = Modifier
) {
    val gridColumns = remember(uiState.viewMode) {
        if (uiState.viewMode == ViewMode.GRID) {
            GridCells.Adaptive(minSize = 160.dp)
        } else {
            GridCells.Fixed(1)
        }
    }

    MovieScaffold (
        movieAppBar = {
            MovieAppBar(
                title = uiState.title,
                showDivider = true,
                showBackButton = true,
                backButtonClick = { interactionListener.onNavigateBack() }
            )
        }
        ,
        movieFloatingActionButton = {
            ViewModeToggleButton(
                selectedMode = uiState.viewMode,
                onModeSelected = interactionListener::onViewModeChanged,
                modifier = Modifier

            )
        }
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,

        ) {

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
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                                            onMovieClick = interactionListener::onMediaItemClicked
                                        )
                                    }
                                }
                            }
                        }
                        if (contentList.loadState.append is LoadState.Loading) {
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

                        if ( contentList.loadState.append is LoadState.Error) {
                            item (span = { GridItemSpan(maxLineSpan) }){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    NoInternetScreen(onRetry = contentList::retry)
                                }
                            }

                    }
                    }
                }
            }
        }
    }
}

