package com.android.domain.repository

import com.android.domain.model.Review

interface ReviewsRepository {
    suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review>

}