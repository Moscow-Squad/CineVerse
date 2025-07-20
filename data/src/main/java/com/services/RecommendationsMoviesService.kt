package com.services

import retrofit2.http.*
import com.remote.dto.*
import com.utils.*

interface RecommendationsMoviesService {
    @GET("$MOVIE{movieID}$RECOMMENDATIONS")
    suspend fun getRecommendations(
        @Path("movieID") movieID: Int,
        @Query("page") page: Int
    ): ApiResponse<MovieDto>
}