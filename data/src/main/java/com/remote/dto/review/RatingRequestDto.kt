package com.remote.dto.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingRequestDto(
    @SerialName("value") val value: Float
)