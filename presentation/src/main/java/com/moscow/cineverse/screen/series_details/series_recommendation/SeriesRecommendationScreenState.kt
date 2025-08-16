package com.moscow.cineverse.screen.series_details.series_recommendation

import androidx.paging.PagingData
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState
import com.moscow.cineverse.utlis.ViewMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SeriesRecommendationScreenState(
    val viewMode: ViewMode = ViewMode.GRID,
    val isLoading: Boolean = false,
    val error: Int? = null,
    val enableBlur: String = "high",
    val seriesGenre: List<ExploreScreenState.GenreUiState> = listOf(),
    val recommendation: Flow<PagingData<MediaItemUiState>> = flowOf()
)