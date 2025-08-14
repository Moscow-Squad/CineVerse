package com.moscow.remote.dto.collection.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCollectionRequestDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
)