package com.moscow.remote.dto.collection.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddMediaItemToCollectionRequestDto(
    @SerialName("media_id") val mediaId: Int,
)