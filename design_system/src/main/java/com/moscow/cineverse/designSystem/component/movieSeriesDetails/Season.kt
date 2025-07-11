package com.moscow.cineverse.designSystem.component.movieSeriesDetails

data class Season (
    val seasonNumber: Int,
    val episodeCount: Int,
    val airDate: String,
    val posterUrl: String? = null,
    val caption: String,
    val rate: Float
)