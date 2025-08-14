package com.moscow.remote.dto.media_item.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1") val iso: String?,
    @SerialName("name") val name: String?,
)
