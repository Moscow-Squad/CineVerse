package com.moscow.cineverse.screen.model

data class MediaItemUi(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val duration: String
)