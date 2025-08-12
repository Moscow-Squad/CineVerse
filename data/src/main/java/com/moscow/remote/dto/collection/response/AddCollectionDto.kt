package com.moscow.remote.dto.collection.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCollectionDto(
    @SerialName("list_id")
    val listId: Int?,
    @SerialName("status_code")
    val statusCode: Int?,
    @SerialName("status_message")
    val statusMessage: String?,
    @SerialName("success")
    val success: Boolean?
)