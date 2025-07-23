package com.remote.services

import retrofit2.http.*
import com.remote.dto.review.*
import com.utils.ApiResponse
import retrofit2.Response

interface ReviewsService {

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<ReviewDto>>

    @GET("tv/{id}/reviews")
    suspend fun getSeriesReviews(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<ReviewDto>>

}