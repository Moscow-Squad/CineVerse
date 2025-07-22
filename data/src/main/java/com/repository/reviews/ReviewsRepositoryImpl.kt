package com.repository.reviews

import com.android.domain.model.Review
import com.android.domain.repository.ReviewsRepository
import com.mapper.toDomain
import com.remote.data_source.ReviewsRemoteDataSourceImpl

class ReviewsRepositoryImpl(
    private val reviewsRemoteDataSourceImpl: ReviewsRemoteDataSourceImpl,
) : ReviewsRepository {

    override suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review> {

        val reviews = reviewsRemoteDataSourceImpl.getReviews(id, page, isMovie)
        return reviews.results.mapNotNull { runCatching { it.toDomain() }.getOrNull() }
    }


}