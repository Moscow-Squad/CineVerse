package com.android.domain.model

import kotlinx.datetime.LocalDate

//data class Series(
//    val id: Int,
//    val name: String,
//    val genresId: List<Int>,
//    val description: String,
//    val rating: Float,
//    val releaseDate: LocalDate,
//    val poster: String,
//)
data class Series(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val voteAverage: Double,
    val genres: String,
    val duration: String,
    val releaseDate: String
)