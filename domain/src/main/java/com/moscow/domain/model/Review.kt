package com.moscow.domain.model

import kotlinx.datetime.LocalDate

data class Review(
    val id: String,
    val author: String,
    val username: String,
    val avatarPath: String,
    val rating: Double,
    val content: String,
    val createdAt: LocalDate?,
)