package com.android.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val name: String,
    val genreIds: List<Int>,
    val rating: Float,
    val releaseDate: LocalDate,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val video: Boolean,
)