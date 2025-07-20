package com.services

import retrofit2.http.*
import com.remote.dto.review.*
import com.utils.*

interface ReviewsService {
    @GET
    suspend fun getReviews(
        @Url url: String,
        @Query("page") page: Int
    ): ReviewDto
}