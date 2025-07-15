package com.repository.mapper

import com.android.domain.model.Review
import com.remote.dto.review.Result

fun Result.toDomain(): Review {
    return Review(
        id = this.id ?: "",
        author = this.author ?: "",
        username = this.authorDetails?.username ?: "",
        avatarPath = this.authorDetails?.avatarPath ?: "",
        rating = this.authorDetails?.rating ?: 0.0,
        content = this.content ?: "",
        createdAt = this.createdAt ?: ""
    )
}