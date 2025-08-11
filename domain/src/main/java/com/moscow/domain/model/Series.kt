package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Series(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val trailerPath: String,
    val backdropPath: String,
    val genres: List<Genre>,
    val genreIds: List<Int>,
    val rating: Float,
    val voteCount: Int,
    val releaseDate: LocalDate?,
    val creators: List<Creator>,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val seasons: List<Season>
) {
    data class Creator(
        val id: Int,
        val name: String,
        val profilePath: String
    )

    data class Season(
        val id: Int,
        val name: String,
        val airDate: LocalDate,
        val episodeCount: Int,
        val posterPath: String,
        val overview: String,
        val rate: Float
    )
}