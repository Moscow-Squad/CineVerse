package com.remote.dto.suggestion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)