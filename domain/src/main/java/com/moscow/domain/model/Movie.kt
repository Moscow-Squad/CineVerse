package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val trailerUrl: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: LocalDate?,
    val genreIds: List<Int>,
    val genres: List<String>,
    val duration: Duration,
    val rating: Float
) {
    data class Duration (
        val hours: Int,
        val minutes: Int
    )
}