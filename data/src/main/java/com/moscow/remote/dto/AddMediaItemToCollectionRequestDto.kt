package com.moscow.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddMediaItemToCollectionRequestDto(
    @SerialName("media_id") val mediaId: Int,
    @SerialName("media_type") val mediaType: String
)
