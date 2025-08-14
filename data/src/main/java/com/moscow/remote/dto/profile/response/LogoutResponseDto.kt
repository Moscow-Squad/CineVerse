package com.moscow.remote.dto.profile.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponseDto(
    @SerialName("success") val success: Boolean? = true,
)