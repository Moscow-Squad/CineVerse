package com.moscow.cineverse.screen.home

import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.common_ui_state.MyCollectionUiState


data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userName: String? = null,
    val sliderItems: List<MediaItemUiState> = emptyList(),
    val recentlyReleasedMovies: List<MediaItemUiState> = emptyList(),
    val upcomingMovies: List<MediaItemUiState> = emptyList(),
    val matchesYourVibe: List<MediaItemUiState> = emptyList(),
    val topRatedTvShows: List<MediaItemUiState> = emptyList(),
    val youRecentlyViewed: List<MediaItemUiState> = emptyList(),
    val collections: List<MyCollectionUiState> = emptyList(),
    val enableBlur: String = "high",
    val cashLanguage: String = "en",
){
    data class GenreUi(
        val id: Int,
        val name: String
    )
}
