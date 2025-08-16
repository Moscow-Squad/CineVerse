package com.moscow.remote.dto.genre

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("genres") val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name")  val name: String
)
