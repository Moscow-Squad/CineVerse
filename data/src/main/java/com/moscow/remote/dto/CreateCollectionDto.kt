package com.moscow.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCollectionDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
)
