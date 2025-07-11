package com.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionResponse(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Result>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)
@Serializable
data class Result(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)