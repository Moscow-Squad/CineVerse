package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.details.SeriesDto
import com.utils.*
import retrofit2.Response

interface RecommendationsService {

    @GET("$MOVIE{movieID}$RECOMMENDATIONS")
    suspend fun getMoviesRecommendations(
        @Path("movieID") movieID: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET("$SERIES{seriesID}$RECOMMENDATIONS")
    suspend fun getSeriesRecommendations(
        @Path("seriesID") movieID: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<SeriesDto>>
}