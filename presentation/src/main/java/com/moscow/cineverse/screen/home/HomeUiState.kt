package com.moscow.cineverse.screen.home

import com.moscow.cineverse.common_ui_state.MediaItemUiState

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val genres: List<GenreUi> = emptyList(),
    val userName: String? = null,
    val sliderItems: List<MediaItemUiState> = emptyList(),
    val recentlyReleasedMovies: List<MediaItemUiState> = emptyList(),
    val upcomingMovies: List<MediaItemUiState> = emptyList(),
    val matchesYourVibe: List<MediaItemUiState> = emptyList(),
    val topRatedTvShows: List<MediaItemUiState> = emptyList(),
    val youRecentlyViewed: List<MediaItemUiState> = emptyList(),
    val collections: List<CollectionUiState> = emptyList()
){
    data class GenreUi(
        val id: Int,
        val name: String
    )

}


data class CollectionUiState(
    val id: Int,
    val title: String,
    val numberOfShows: Int,
)
