package com.remote.dto.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Result?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)