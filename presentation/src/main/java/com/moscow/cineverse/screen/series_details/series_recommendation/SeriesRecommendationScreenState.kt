package com.moscow.cineverse.screen.series_details.series_recommendation

import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cineverse.utlis.ViewMode

data class SeriesRecommendationScreenState(
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: Int? = null,
    val enableBlur: String = "high",
    val seriesGenre: List<ExploreScreenState.GenreUiState> = listOf()
)