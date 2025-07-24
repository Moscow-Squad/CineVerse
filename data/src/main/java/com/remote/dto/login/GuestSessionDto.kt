package com.remote.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSessionDto(
    @SerialName("success") val success: Boolean,
    @SerialName("guest_session_id") val guestSessionId: String? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("status_code") val statusCode: Int? = null,
    @SerialName("status_message") val statusMessage: String? = null
)
