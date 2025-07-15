package com.remote.dto.review


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetails(
    @SerialName("avatar_path")
    val avatarPath: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("rating")
    val rating: Double?,
    @SerialName("username")
    val username: String?
)