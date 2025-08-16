package com.moscow.remote.dto.profile.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("username") val userName: String? = null,
    @SerialName("avatar") val avatar: Avatar? = null,
    @SerialName("success") val success: Boolean? = true,
    @SerialName("status_message") val statusMessage: String? = null
)

@Serializable
data class Avatar(
    @SerialName("gravatar") val gravatar: Gravatar? = null,
    @SerialName("tmdb") val tmdb: Tmdb? = null
)

@Serializable
data class Gravatar(
    @SerialName("hash") val hash: String? = null
)

@Serializable
data class Tmdb(
    @SerialName("avatar_path") val avatarPath: String? = null
)