package com.mapper

import com.android.domain.model.Review
import com.remote.dto.review.Result
import com.utils.IMAGES_URL

fun Result.toDomain(): Review {
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