package com.moscow.cineverse.common_ui_state

data class MediaItemUiState(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val mediaType: MediaType,
    val backdropPath: String

){
    enum class MediaType {
        MOVIE, SERIES
    }
}