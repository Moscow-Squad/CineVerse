package com.remote.data_source

import com.data_source.remote.ReviewsRemoteDataSource
import com.remote.dto.review.ReviewDto
import com.remote.services.ReviewsService
import com.utils.ApiResponse
import com.utils.handleApi


class ReviewsRemoteDataSourceImpl(
    private val reviewsService: ReviewsService
) : ReviewsRemoteDataSource {
    override suspend fun getReviews(id: Int, page: Int, isMovie: Boolean): ApiResponse<ReviewDto> =
        handleApi {
            if (isMovie)
                reviewsService.getMovieReviews(id, page)
            else
                reviewsService.getSeriesReviews(id, page)
        }
}