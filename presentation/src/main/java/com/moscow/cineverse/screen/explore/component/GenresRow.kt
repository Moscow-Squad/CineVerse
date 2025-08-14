package com.moscow.cineverse.screen.explore.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cineverse.screen.explore.ExploreTabsPages
import com.moscow.cinverse.presentation.R

@Composable
fun GenresRow(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    if (uiState.shouldShowGenres) {
        // Animated height for collapse effect
        val height by animateDpAsState(
            targetValue = if (isVisible) 68.dp else 0.dp,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "genresRowHeight"
        )

        // Animated alpha for smooth fade
        val alpha by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "genresRowAlpha"
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height) // Use animated height instead of fixed height
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
                .alpha(alpha) // Use animated alpha
        ) {
            // Only render content when height > 0
            if (height > 0.dp) {
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
                text = if (genre.name == "All") LocalContext.current.getString(R.string.all) else genre.name,
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

@Composable
fun PillLabel(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    prefixIcon: @Composable () -> Unit = {},
    suffixIcon: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(if (isActive) Theme.colors.brand.tertiary else Theme.colors.background.card)
            .then(
                if (isActive) {
                    Modifier.border(
                        width = 1.dp,
                        color = Theme.colors.brand.secondary,
                        shape = RoundedCornerShape(Theme.radius.full)
                    )
                } else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            prefixIcon()

            Text(
                text = text,
                color = if (isActive) Theme.colors.brand.primary else Theme.colors.shade.secondary,
                style = Theme.textStyle.label.medium.medium
            )

            suffixIcon()
        }
    }
}