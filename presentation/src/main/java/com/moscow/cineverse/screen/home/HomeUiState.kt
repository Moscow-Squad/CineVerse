package com.moscow.cineverse.screen.home

import com.moscow.cineverse.screen.model.MediaItemUi

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userName: String = "",
    val sliderItems: List<MediaItemUi> = emptyList(),
    val recentlyReleasedMovies: List<MediaItemUi> = emptyList(),
    val upcomingMovies: List<MediaItemUi> = emptyList(),
    val matchesYourVibe: List<MediaItemUi> = emptyList(),
    val topRatedTvShows: List<MediaItemUi> = emptyList(),
    val youRecentlyViewed: List<MediaItemUi> = emptyList(),
    val collections: List<CollectionUiState> = emptyList()
)


data class CollectionUiState(
    val id: Int,
    val title: String,
    val numberOfShows: Int,
)
