package com.remote.services

import com.remote.dto.CreditsDetailsDto
import com.remote.dto.MovieDto
import com.remote.dto.details.MovieDetailDto
import com.remote.dto.review.RatingRequestDto
import com.remote.dto.review.ReviewDto
import com.utils.ApiResponse
import com.utils.CREDITS
import com.utils.DISCOVER_MOVIE_LIST
import com.utils.MOVIE
import com.utils.PAGE
import com.utils.POPULAR
import com.utils.RATING
import com.utils.RECOMMENDATIONS
import com.utils.REVIEWS
import com.utils.SESSION_ID
import com.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("$MOVIE$POPULAR")
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>


    @GET("$MOVIE{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): Response<MovieDetailDto>

    @POST("$MOVIE{movie_id}$RATING")
    suspend fun rateMovie(
        @Path("movie_id") id: Int,
        @Query(SESSION_ID) sessionId: String,
        @Body rating: RatingRequestDto
    ): Response<Nothing>

    @GET("$MOVIE{movie_id}$CREDITS")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int
    ): Response<CreditsDetailsDto>

    @GET("$MOVIE{id}$REVIEWS")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query(PAGE) page: Int
    ): Response<ApiResponse<ReviewDto>>

    @GET("$MOVIE{movie_id}$RECOMMENDATIONS")
    suspend fun getMoviesRecommendations(
        @Path("movie_id") id: Int,
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET(DISCOVER_MOVIE_LIST)
    suspend fun getMoviesByGenreId(
        @Query(WITH_GENRES) genreId: Int,
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>
}