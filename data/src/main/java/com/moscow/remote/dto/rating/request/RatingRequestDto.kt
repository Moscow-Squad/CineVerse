package com.moscow.remote.dto.rating.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingRequestDto(
    @SerialName("value") val value: Float
)