package com.moscow.remote.dto.collections_v4


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmptyCollectionApiResponse(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String? = null,
    @SerialName("success")
    val success: Boolean
)

@Serializable
data class Result(
    @SerialName("media_id")
    val mediaId: Int,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("success")
    val success: Boolean? = null
)