package com.moscow.cineverse.screen.component.movie_poster_card

data class MediaItemUi(
    val id: Int,
    val title: String,
    val posterPath: String,
    val rating: Float,
    val genres: List<String>,
    val releaseDate: String,
    val duration: String
)