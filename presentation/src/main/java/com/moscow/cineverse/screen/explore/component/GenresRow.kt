package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.designSystem.component.PillLabel
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun GenresRow(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    if (uiState.shouldShowGenres) {
        val alpha by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f,
            animationSpec = tween(300),
            label = "genresAlpha"
        )

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(300)
            ),
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.colors.background.screen,
                                Theme.colors.background.screen.copy(alpha = 0.95f),
                                Theme.colors.background.screen.copy(alpha = 0.8f),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = 80f
                        )
                    )
                    .alpha(alpha)
            ) {
                GenresRowContent(
                    uiState = uiState,
                    genresState = genresState,
                    interactionListener = interactionListener
                )
            }
        }
    }
}

@Composable
private fun GenresRowContent(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener
) {
    val selectedGenre = if (uiState.selectedTab == ExploreTabsPages.MOVIES) {
        uiState.selectedMovieGenre
    } else {
        uiState.selectedSeriesGenre
    }

    LazyRow(
        state = genresState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 12.dp)
    ) {
        items(uiState.genres) { genre ->
            PillLabel(
                text = genre.name,
                isActive = selectedGenre == genre.id,
                onClick = {
                    if (uiState.selectedTab == ExploreTabsPages.SERIES) {
                        interactionListener.onSeriesGenreSelected(genre.id)
                    } else {
                        interactionListener.onMovieGenreSelected(genre.id)
                    }
                }
            )
        }
    }
}