package com.android.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Long,
    val name: String,
    val genresId: List<Int>,
    val description: String,
    val rating: Float,
    val releaseDate: LocalDate,
    val poster: String,
    val duration: String ,
)