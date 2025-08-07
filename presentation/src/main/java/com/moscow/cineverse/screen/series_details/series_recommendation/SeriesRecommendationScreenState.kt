package com.moscow.cineverse.screen.series_details.series_recommendation

import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cineverse.utlis.ViewMode

data class SeriesRecommendationScreenState(
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: String? = null,
    val enableBlur: Boolean = true,
    val seriesGenre: List<ExploreScreenState.GenreUiState> = listOf()
)