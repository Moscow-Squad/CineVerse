package com.moscow.cineverse.screen.myratings

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.screen.explore.ExploreScreenState.GenreUiState
import com.moscow.cineverse.screen.explore.ExploreTabsPages

data class MyRatingsUiState(
    val selectedTab: ExploreTabsPages = ExploreTabsPages.MOVIES,
    val isLoading: Boolean = false,
    val shouldShowError: Boolean = false,
    val errorMessage: String = "",
    val ratedMediaItems: List<RatedMediaItem> = emptyList(),
    val movieGenres: List<GenreUiState> = emptyList(),
    val seriesGenres: List<GenreUiState> = emptyList(),
    val isContentEmpty: Boolean = false,
    val enableBlur: Boolean = true
)

data class RatedMediaItem(
    val mediaItem: MediaItemUiState,
    val rating: Int
)