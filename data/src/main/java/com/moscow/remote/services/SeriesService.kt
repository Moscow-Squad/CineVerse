package com.moscow.remote.services

import com.moscow.remote.dto.details.SeriesCreditDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.remote.dto.series.ListOfSeriesDto
import com.moscow.remote.dto.series.SeriesDetailDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.remote.dto.details.MediaTrailersDto
import com.moscow.remote.dto.rating.UserRatingResponse
import com.moscow.remote.dto.rating.series.RatedSeriesDto
import com.moscow.utils.ACCOUNT
import com.moscow.utils.ACCOUNT_STATES
import com.moscow.utils.ApiResponse
import com.moscow.utils.DESCENDING
import com.moscow.utils.DISCOVER_SERIES_LIST
import com.moscow.utils.LATEST
import com.moscow.utils.LISTS
import com.moscow.utils.PAGE
import com.moscow.utils.POPULAR
import com.moscow.utils.RATED_SERIES
import com.moscow.utils.RATING
import com.moscow.utils.RECOMMENDATIONS
import com.moscow.utils.REVIEWS
import com.moscow.utils.SERIES
import com.moscow.utils.SERIES_CREDITS
import com.moscow.utils.SESSION_ID
import com.moscow.utils.SORTED_BY
import com.moscow.utils.TRAILERS
import com.moscow.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
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
    ): Response<Unit>

    @DELETE("$SERIES{series_id}$RATING")
    suspend fun deleteRatingSeries(
        @Path("series_id") seriesId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<Unit>

    @GET("$SERIES{series_id}$ACCOUNT_STATES")
    suspend fun getUserRatingForSeries(
        @Path("series_id") seriesId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<UserRatingResponse>

    @GET("$ACCOUNT/{account_id}$RATED_SERIES")
    suspend fun getRatedSeries(
        @Path("account_id") id: Int,
        @Query(SESSION_ID) sessionId: String,
        @Query(PAGE) page: Int,
        @Query(SORTED_BY) sortBy: String = DESCENDING
    ): Response<ApiResponse<RatedSeriesDto>>

    @GET("$SERIES$LATEST")
    suspend fun getLatestSeasons()
            : Response<SeriesDetailDto>

    @GET("$SERIES{id}$LISTS")
    suspend fun getListOfSeries(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): Response<ListOfSeriesDto>

    @GET("$SERIES{id}$REVIEWS")
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

    @GET("$SERIES{series_id}$SERIES_CREDITS")
    suspend fun getSeriesCredits(
        @Path("series_id") seriesId: Int
    ): Response<SeriesCreditDto>

    @GET("$SERIES{series_id}$TRAILERS")
    suspend fun getSeriesTrailers(
        @Path("series_id") seriesId: Int
    ): Response<MediaTrailersDto>
}
