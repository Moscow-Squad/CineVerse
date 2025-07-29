package com.moscow.cineverse.screen.series_details.series_recommendation

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.ViewMode

data class SeriesRecommendationScreenState (
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val viewMode: ViewMode = ViewMode.GRID,
    val recommendation: List<MediaItemUiState> = emptyList(),
    )