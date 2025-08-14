package com.moscow.cineverse.common_ui_state

data class MediaItemUiState(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val backdropPath: String,
    val mediaType: MediaType
) {
    enum class MediaType {
        MOVIE, SERIES
    }
}