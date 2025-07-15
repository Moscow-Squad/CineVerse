package com.android.domain.model

data class SeriesDetail(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val genres: List<String>,
    val voteAverage: Double,
    val firstAirDate: String?,
)
