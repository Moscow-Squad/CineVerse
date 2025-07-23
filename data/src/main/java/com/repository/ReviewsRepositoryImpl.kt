package com.repository

import com.android.domain.model.Review
import com.android.domain.repository.ReviewsRepository
import com.data_source.remote.ReviewsRemoteDataSource
import com.mapper.toDomain

class ReviewsRepositoryImpl(
    private val reviewsRemoteDataSource: ReviewsRemoteDataSource,
) : ReviewsRepository {

    override suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review> {

        val reviews = reviewsRemoteDataSource.getReviews(id, page, isMovie)
        return reviews.results?.mapNotNull { runCatching { it.toDomain() }.getOrNull() } ?: emptyList()
    }
}