package com.repository.reviews

import com.android.domain.model.Review
import com.android.domain.repository.ReviewsRepository
import com.mapper.toDomain
import com.remote.source.ReviewsRemoteDataSource

class ReviewsRepositoryImpl(
    private val reviewsRemoteDataSource: ReviewsRemoteDataSource,
) : ReviewsRepository {

    override suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review> {

        val reviews = reviewsRemoteDataSource.getReviews(id, page, isMovie)
        return reviews.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
    }


}