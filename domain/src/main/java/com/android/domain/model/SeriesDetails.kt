package com.android.domain.model

data class SeriesDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: List<Genre>,
    val rating: Double,
    val voteCount: Int,
    val runtime: String?,
    val releaseDate: String?,
    val type: String,
    val cast: List<CastMember>,
    val creators: List<Creator>,
    val tagline: String?,
    val status: String,
    val numberOfSeasons: Int?,
    val numberOfEpisodes: Int?,
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String?,
    val profilePath: String?
)

data class Creator(
    val id: Int,
    val name: String,
    val profilePath: String?
)