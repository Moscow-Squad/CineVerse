package com.remote.dto.series

import com.remote.dto.GenreDto
import com.remote.dto.details.CreatedByDto
import com.remote.dto.details.LastEpisodeToAirDto
import com.remote.dto.details.NetworkDto
import com.remote.dto.details.ProductionCompanyDto
import com.remote.dto.details.ProductionCountryDto
import com.remote.dto.details.SpokenLanguageDto
import com.remote.dto.review.ReviewDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetailDto(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("created_by")
    val createdBy: List<CreatedByDto>? = null,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int> ? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("genres")
    val genres: List<GenreDto> ? = null,
    @SerialName("homepage")
    val homepage: String? = null,
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("languages")
    val languages: List<String> ? = null,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirDto? = null,
    @SerialName("name")
    val name: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: String? = null,
    @SerialName("networks")
    val networks: List<NetworkDto> ? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String> ? = null,
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
    val productionCompanies: List<ProductionCompanyDto> ? = null,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto> ? = null,
    @SerialName("seasons")
    val seasons: List<SeasonDto> ? = null,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto> ? = null,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("type")
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("reviews")
    val reviews: List<ReviewDto> ? = null,
)

