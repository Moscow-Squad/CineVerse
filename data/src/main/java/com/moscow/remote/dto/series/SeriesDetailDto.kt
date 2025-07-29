package com.moscow.remote.dto.series

import com.moscow.remote.dto.GenreDto
import com.moscow.remote.dto.details.CreatedByDto
import com.moscow.remote.dto.details.LastEpisodeToAirDto
import com.moscow.remote.dto.details.NetworkDto
import com.moscow.remote.dto.details.ProductionCompanyDto
import com.moscow.remote.dto.details.ProductionCountryDto
import com.moscow.remote.dto.details.SpokenLanguageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailDto(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("created_by")
    val createdBy: List<CreatedByDto>,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirDto? = null,
    @SerialName("name")
    val name: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: LastEpisodeToAirDto? = null,
    @SerialName("networks")
    val networks: List<NetworkDto>,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto>,
    @SerialName("seasons")
    val seasons: List<SeasonDto>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("type")
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)
