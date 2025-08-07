package com.moscow.remote.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutDto(
    @SerialName("success") val success: Boolean? = true,
)
