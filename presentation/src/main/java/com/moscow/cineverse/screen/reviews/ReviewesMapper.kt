package com.moscow.cineverse.screen.reviews

import com.android.domain.model.Review
import com.moscow.cineverse.screen.movie_details.MovieScreenState.ReviewUi

fun Review.toUi(): ReviewUi =
    ReviewUi(
        id = id,
        username = username,
        name = author,
        userImage = avatarPath,
        rate = rating.toInt(),
        reviewContent = content,
        date=createdAt
    )
