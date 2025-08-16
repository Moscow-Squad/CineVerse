package com.moscow.remote.dto.review

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("author")
    val author: String?,
    @SerialName("author_details")
    val authorDetailsDto: AuthorDetailsDto?,
    @SerialName("content")
    val content: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("url")
    val url: String?
)

@Serializable
data class AuthorDetailsDto(
    @SerialName("avatar_path")
    val avatarPath: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("rating")
    val rating: Double?,
    @SerialName("username")
    val username: String?
)
