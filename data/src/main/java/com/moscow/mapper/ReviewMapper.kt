package com.moscow.mapper

import com.moscow.domain.model.Review
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.IMAGES_URL
import kotlinx.datetime.LocalDate

fun ReviewDto.toDomain(): Review {
    return Review(
        id = this.id ?: "",
        author = this.author ?: "",
        username = this.authorDetailsDto?.username ?: "",
        avatarPath = if(this.authorDetailsDto?.avatarPath.isNullOrEmpty()) "" else IMAGES_URL + this.authorDetailsDto.avatarPath ,
        rating = this.authorDetailsDto?.rating ?: 0.0,
        content = this.content ?: "",
        createdAt = if (!createdAt.isNullOrBlank()) LocalDate.parse(createdAt) else null,
    )
}