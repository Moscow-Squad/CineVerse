package com.android.domain.model

data class Review(
    val id: String,
    val author: String,
    val username: String,
    val avatarPath: String,
    val rating: Double,
    val content: String,
    val createdAt: String,
)
