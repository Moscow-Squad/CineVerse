package com.android.domain.model.details

import com.android.domain.model.Genre
import kotlinx.datetime.LocalDate

data class SeriesDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val genres: List<Genre>,
    val rating: Float,
    val runtime: String,
    val releaseDate: LocalDate,
    val type: String,
    val creators: List<Creator>,
    val tagline: String,
    val status: String,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val lastAirDate: String,
    val nextAirDate: String,
    val seasons: List<Season>,
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String
)

data class Creator(
    val id: Int,
    val name: String,
    val profilePath: String
)
data class Episode(
    val id: Int,
    val name: String,
    val overview: String,
    val airDate: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
)

data class Season(
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeCount: Int,
    val posterPath: String,
    val overview: String,
    val rate: Float
)