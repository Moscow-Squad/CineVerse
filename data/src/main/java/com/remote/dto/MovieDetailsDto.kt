package com.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val runtime: Int?,
    val genres: List<GenreDto>
)