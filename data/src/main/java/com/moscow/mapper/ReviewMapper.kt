package com.moscow.mapper

import com.moscow.domain.model.Review
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.IMAGES_URL

fun ReviewDto.toDomain(): Review {
    return Review(
        id = this.id ?: "",
        author = this.author ?: "",
        username = this.authorDetails?.username ?: "",
        avatarPath = if(this.authorDetails?.avatarPath.isNullOrEmpty()) "" else IMAGES_URL + this.authorDetails.avatarPath ,
        rating = this.authorDetails?.rating ?: 0.0,
        content = this.content ?: "",
        createdAt = createdAt?:""
    )
}