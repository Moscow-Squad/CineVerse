package com.moscow.remote.dto.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastEpisodeToAirDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("air_date") val airDate: String,
    @SerialName("episode_number") val episodeNumber: Int,
    @SerialName("episode_type") val episodeType: String,
    @SerialName("production_code") val productionCode: String,
    @SerialName("runtime") val runtime: Int,
    @SerialName("season_number") val seasonNumber: Int,
    @SerialName("show_id") val showId: Int,
    @SerialName("still_path") val stillPath: String? = null,
)
