package com.remote.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("success") val success: Boolean,
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("failure") val failure: Boolean? = null,
    @SerialName("status_code") val statusCode: Int? = null,
    @SerialName("status_message") val statusMessage: String? = null
)
