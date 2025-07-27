package com.moscow.cineverse.common_ui_state

import com.moscow.domain.model.MediaType

data class MediaItemUiState(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val duration: String,
    val mediaType: MediaType,
    val backdropPath: String
)