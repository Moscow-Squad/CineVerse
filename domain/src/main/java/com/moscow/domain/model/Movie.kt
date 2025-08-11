package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val trailerUrl: String,
    val backdropPath: String,
    val posterPath: String,
    val rating: Float,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val genres: List<Genre>,
    val duration: Duration
) {
    data class Duration (
        val hours: Int,
        val minutes: Int
    )
}