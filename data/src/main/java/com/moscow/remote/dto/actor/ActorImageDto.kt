package com.moscow.remote.dto.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorImagesDto(
    @SerialName("id")
    val id: Int,
    @SerialName("profiles")
    val images: List<ActorImageDetails>
) {
    @Serializable
    data class ActorImageDetails(
        @SerialName("aspect_ratio")
        val aspectRatio: Double?,
        @SerialName("file_path")
        val filePath: String?,
        @SerialName("height")
        val height: Int?,
        @SerialName("vote_average")
        val voteAverage: Double?,
        @SerialName("vote_count")
        val voteCount: Int?,
        @SerialName("width")
        val width: Int?
    )
}
