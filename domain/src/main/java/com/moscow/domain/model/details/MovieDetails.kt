package com.moscow.domain.model.details

import kotlinx.datetime.LocalDate

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val trailerPath: String,
    val posterPath: String,
    val releaseDate: LocalDate,
    val voteAverage: Double,
    val genres: List<String>,
    val duration:Int,
)
