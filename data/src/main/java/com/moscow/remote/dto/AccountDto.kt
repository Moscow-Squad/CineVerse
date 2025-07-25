package com.moscow.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name : String? = null,
    @SerialName("username") val userName: String? = null,
    @SerialName("success") val success: Boolean? = true,
    @SerialName("status_message") val statusMessage: String? = null
)
