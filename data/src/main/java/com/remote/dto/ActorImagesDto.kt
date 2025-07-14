package com.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorImagesDto(
    @SerialName("aspect_ratio")
    val aspectRatio: Double?,
    @SerialName("file_path")
    val filePath: String?,
    @SerialName("height")
    val height: Int?,
    @SerialName("iso_639_1")
    val iso6391: Int?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("width")
    val width: Int?
)

@Serializable
data class ActorImagesResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("profiles")
    val profiles: List<ActorImagesDto>
)