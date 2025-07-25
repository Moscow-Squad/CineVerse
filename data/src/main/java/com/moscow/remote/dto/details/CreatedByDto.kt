package com.moscow.remote.dto.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedByDto(
    @SerialName("id")
    val id: Int,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("name")
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("profile_path")
    val profilePath: String,
)
