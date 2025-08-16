package com.moscow.cineverse.screen.see_more.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.moscow.cineverse.component.MediaPosterCard
import com.moscow.cineverse.component.NoInternetScreen
import com.moscow.cineverse.designSystem.component.indicator.MovieCircularProgressBar
import com.moscow.cineverse.screen.see_more.SeeMoreInteractionListener
import com.moscow.cineverse.screen.see_more.SeeMoreUiState

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun <T : Any> ContentGrid(
    uiState: SeeMoreUiState,
    gridState: LazyGridState,
    gridColumns: GridCells,
    contentList: LazyPagingItems<T>,
    interactionListener: SeeMoreInteractionListener,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
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
                        MediaPosterCard(
                            mediaItem = safeItem,
                            viewMode = uiState.viewMode,
                            enableBlur = uiState.enableBlur,
                            onMediaItemClick = { interactionListener.onMediaItemClicked(safeItem.id) },
                            // Pass the shared transition scopes
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            }
        }
        if (contentList.loadState.append is LoadState.Loading && contentList.itemCount > 20) {
            item (span = {GridItemSpan(maxLineSpan)}) {
                Box(
                    modifier = Modifier.height(214.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MovieCircularProgressBar(Modifier.align(Alignment.Center))
                }
            }
        }

        if (contentList.loadState.append is LoadState.Error) {
            item (span = { GridItemSpan(maxLineSpan) }) {
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