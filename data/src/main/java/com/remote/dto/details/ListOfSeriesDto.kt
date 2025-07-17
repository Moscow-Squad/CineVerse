package com.remote.dto.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListOfSeriesDto(
    @SerialName("id")
    val id: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)

@Serializable
data class Result(
    @SerialName("description")
    val description: String,
    @SerialName("favorite_count")
    val favoriteCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("item_count")
    val itemCount: Int,
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("name")
    val name: String,
    @SerialName("poster_path")
    val posterPath: String?,
)