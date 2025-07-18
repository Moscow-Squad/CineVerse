package com.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ApiResponse<T>(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<T>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int,

    @SerialName("status_code")
    val statusCode: Int? = null,

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("status_message")
    val statusMessage: String? = null,

)