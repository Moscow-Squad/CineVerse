package com.data_source.remote

import com.utils.ApiResponse
import com.remote.dto.review.ReviewDto

interface ReviewsRemoteDataSource {
    suspend fun getReviews(id: Int, page: Int, isMovie: Boolean): ApiResponse<ReviewDto>
}