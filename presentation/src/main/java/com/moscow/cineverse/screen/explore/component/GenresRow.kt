package com.moscow.cineverse.screen.explore.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.PillLabel
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.screen.explore.ExploreInteractionListener
import com.moscow.cineverse.screen.explore.ExploreScreenState

@Composable
fun GenresRow(
    uiState: ExploreScreenState,
    genresState: LazyListState,
    interactionListener: ExploreInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.shouldShowGenres) {
        val selectedGenre =
            if (uiState.selectedTab == ExploreTabsPages.MOVIES) uiState.selectedMovieGenre
            else uiState.selectedSeriesGenre

        LazyRow(
            state = genresState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(uiState.genres) { genre ->
                PillLabel(
                    text = genre.name,
                    isActive = selectedGenre == genre.id,
                    onClick = {
                        if (uiState.selectedTab == ExploreTabsPages.SERIES)
                            interactionListener.onSeriesGenreSelected(genre.id)
                        else
                            interactionListener.onMovieGenreSelected(genre.id)
                    }
                )
            }
        }
    }
}
