package com.android.domain.model.details

import com.android.domain.model.Genre
import com.android.domain.model.Review

data class SeriesDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: List<Genre>,
    val rating: Double,
    val voteCount: Int,
    val releaseDate: String?,
    val type: String,
    val cast: List<CastMember>,
    val creators: List<Creator>,
    val tagline: String?,
    val status: String,
    val numberOfSeasons: Int?,
    val numberOfEpisodes: Int?,
    val lastAirDate: String?,
    val nextAirDate: String?,
    val lastEpisodeToAir: Episode?,
    val nextEpisodeToAir: Episode?,
    val reviews: List<Review>,
    val similarSeries: List<SeriesDetail>,
    val seasons: List<Season>,
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
data class Episode(
    val id: Int,
    val name: String,
    val overview: String,
    val airDate: String?,
    val episodeNumber: Int,
    val seasonNumber: Int,
)

data class Season(
    val id: Int,
    val name: String,
    val airDate: String?,
    val episodeCount: Int,
    val posterPath: String?,
)