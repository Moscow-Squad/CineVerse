package com.moscow.cineverse.screen.see_more.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.component.MoviePosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.see_more.SeeMoreInteractionListener
import com.moscow.cineverse.screen.see_more.SeeMoreUiState

@Composable
fun <T : Any> ContentGrid(
    uiState: SeeMoreUiState,
    gridState: LazyGridState,
    gridColumns: GridCells,
    contentList: LazyPagingItems<T>,
    interactionListener: SeeMoreInteractionListener,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        state = gridState,
        columns = gridColumns,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(contentList.itemCount) { index ->
            val item = contentList[index]
            item?.let { safeItem ->
                when (safeItem) {
                    is MediaItemUiState -> {
                        MoviePosterCard(
                            movie = safeItem,
                            viewMode = uiState.viewMode,
                            onMovieClick = interactionListener::onMediaItemClicked,
                            enableBlur = uiState.enableBlur
                        )
                    }
                }
            }
        }

        if (contentList.loadState.append is LoadState.Loading && contentList.itemCount > 20) {
            item {
                Box(
                    modifier = Modifier.wrapContentSize(),
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

        if (contentList.loadState.append is LoadState.Error) {
            item(span = { GridItemSpan(maxLineSpan) }) {
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