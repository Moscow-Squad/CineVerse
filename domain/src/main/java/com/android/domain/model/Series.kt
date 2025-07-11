package com.android.domain.model

import kotlinx.datetime.LocalDate

data class Series(
    val id: Int,
    val name: String,
    val rating: Float,
    val poster: String,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: LocalDate,
    val genreIds: List<Int>,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val posterPath: String,
)