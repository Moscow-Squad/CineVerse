package com.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val name: String,
    val genresId: List<Int>,
    val description: String,
    val rating: Float,
    val releaseDate: LocalDate,
    val poster: String,
)