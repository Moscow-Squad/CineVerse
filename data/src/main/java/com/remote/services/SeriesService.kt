package com.remote.services

import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.remote.dto.series.SeriesDto
import com.utils.ApiResponse
import com.utils.DISCOVER_SERIES_LIST
import com.utils.LATEST
import com.utils.LISTS
import com.utils.PAGE
import com.utils.POPULAR
import com.utils.RATING
import com.utils.RECOMMENDATIONS
import com.utils.SERIES
import com.utils.SESSION_ID
import com.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {
    @GET("$SERIES$POPULAR")
    suspend fun getPopularSeries(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<SeriesDto>>

    @GET("$SERIES{id}")
    suspend fun getSeriesDetails(
        @Path("id") id: Int
    ): Response<SeriesDetailDto>

    @POST("$SERIES{series_id}$RATING")
    suspend fun rateSeries(
        @Path("series_id") id: Int,
        @Query(SESSION_ID) sessionId: String,
        @Body rating: RatingRequestDto
    ): Response<Nothing>

    @GET("$SERIES$LATEST")
    suspend fun getLatestSeasons()
            : Response<SeriesDetailDto>

    @GET("$SERIES{id}$LISTS")
    suspend fun getListOfSeries(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<ListOfSeriesDto>

    @GET("series/{id}/reviews")
    suspend fun getSeriesReviews(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<ReviewDto>>

    @GET("$SERIES{series_id}$RECOMMENDATIONS")
    suspend fun getSeriesRecommendations(
        @Path("series_id") id: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<SeriesDto>>

    @GET(DISCOVER_SERIES_LIST)
    suspend fun getSeriesByGenreId(
        @Query(WITH_GENRES) genreId: Int,
        @Query(PAGE) page: Int
    ): Response<ApiResponse<SeriesDto>>
}