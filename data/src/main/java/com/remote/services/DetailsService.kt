package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.details.*
import com.remote.dto.review.*
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.utils.CREDITS
import com.utils.LATEST
import com.utils.LISTS
import com.utils.MOVIE
import com.utils.RATING
import com.utils.SERIES
import com.utils.SESSION_ID
import retrofit2.Response

interface DetailsService {

    @GET("$MOVIE{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): Response<MovieDetailDto>

    @GET("$SERIES{id}")
    suspend fun getSeriesDetails(
        @Path("id") id: Int
    ): Response<SeriesDetailDto>

    @GET("$MOVIE{movie_id}$CREDITS")
    suspend fun getCredits(
        @Path("movie_id") movieID: Int
    ): Response<CreditsDetailsDto>


    @POST("$MOVIE{movie_id}$RATING")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query(SESSION_ID) sessionId: String,
        @Body rating: RatingRequestDto
    ): Response<Nothing>

    @POST("$SERIES{series_id}$RATING")
    suspend fun rateSeries(
        @Path("series_id") seriesId: Int,
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
}