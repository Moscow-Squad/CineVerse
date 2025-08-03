package com.moscow.remote.dto.rating.series


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatedSeriesResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val ratedSeriesDtos: List<RatedSeriesDto?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)