package com.moscow.remote.dto.rating.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatedMoviesResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val ratedMovieDtos: List<RatedMovieDto?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)