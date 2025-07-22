package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.series.SeriesDto
import com.utils.*
import retrofit2.Response

interface RecommendationsService {

    @GET("$MOVIE{movie_id}$RECOMMENDATIONS")
    suspend fun getMoviesRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET("$SERIES{series_id}$RECOMMENDATIONS")
    suspend fun getSeriesRecommendations(
        @Path("series_id") movieId: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<SeriesDto>>
}