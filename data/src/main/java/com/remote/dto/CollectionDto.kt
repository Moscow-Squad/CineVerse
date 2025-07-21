package com.remote.dto

import com.android.domain.model.Collection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    @SerialName("description") val description: String? = null,
    @SerialName("favoriteCount") val favoriteCount: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("iso_639_1") val iso6391: String? = null,
    @SerialName("itemCount") val itemCount: Int? = null,
    @SerialName("list_type") val listType: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
)

fun CollectionDto.toDomain() =
    Collection(
        id = id ?: 0,
        name = name ?: ""
    )