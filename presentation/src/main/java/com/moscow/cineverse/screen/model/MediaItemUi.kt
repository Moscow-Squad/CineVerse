package com.moscow.cineverse.screen.model

import com.android.domain.model.MediaType

data class MediaItemUi(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val duration: String,
    val mediaType: MediaType
)