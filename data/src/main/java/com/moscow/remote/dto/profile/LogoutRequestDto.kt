package com.moscow.remote.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LogoutRequestDto(
    @SerialName("session_id") val sessionId:String,
)
