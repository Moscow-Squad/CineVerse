package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val name: String,
    val genreIds: List<Int>,
    val rating: Float,
    val releaseDate: LocalDate?,
    val adult: Boolean,
    val posterPath: String,
    val video: Boolean,
    val poster: String,
)
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val trailerPath: String,
    val posterPath: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val genres: List<String>,
    val duration:Int,
)
