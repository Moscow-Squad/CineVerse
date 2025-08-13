package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val trailerUrl: String,
    val backdropUrl: String,
    val posterUrl: String,
    val releaseDate: LocalDate?,
    val genreIds: List<Int>,
    val genres: List<String>,
    val rating: Float
)