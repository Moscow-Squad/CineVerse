package com.remote.services

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.details.SeriesCreditDto
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.series.ListOfSeriesDto
import com.remote.dto.series.SeriesDetailDto
import com.utils.CREDITS
import com.utils.LATEST
import com.utils.LISTS
import com.utils.MOVIE
import com.utils.RATING
import com.utils.SERIES
import com.utils.SERIES_CREDITS
import com.utils.SESSION_ID
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("$SERIES{series_id}$SERIES_CREDITS")
    suspend fun getSeriesCredits(
        @Path("series_id") seriesId: Int
    ): Response<SeriesCreditDto>
}