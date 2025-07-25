package com.moscow.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ApiResponse<T>(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<T>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null,

    @SerialName("status_code")
    val statusCode: Int? = null,

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("status_message")
    val statusMessage: String? = null,
)
