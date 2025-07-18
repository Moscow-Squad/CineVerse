package com.android.domain.model.details

import kotlinx.datetime.LocalDate

data class SeriesSeason(
    val id: Int,
    val number: Int,
    val seriesId: Int,
    val episodesCount: Int,
    val rating: Float,
    val description: String,
    val releaseDate: LocalDate,
    val poster: String,
)