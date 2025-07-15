package com.android.domain.model

data class MultiSearch(
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: MediaType,
    val name: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val rate: Float,
)