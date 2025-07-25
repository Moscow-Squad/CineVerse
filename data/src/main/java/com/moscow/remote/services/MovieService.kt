package com.moscow.remote.services

import com.moscow.remote.dto.CreditsDetailsDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.details.MovieDetailDto
import com.moscow.remote.dto.review.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.CREDITS
import com.moscow.utils.DISCOVER_MOVIE_LIST
import com.moscow.utils.MOVIE
import com.moscow.utils.PAGE
import com.moscow.utils.POPULAR
import com.moscow.utils.RATING
import com.moscow.utils.RECOMMENDATIONS
import com.moscow.utils.REVIEWS
import com.moscow.utils.SESSION_ID
import com.moscow.utils.WITH_GENRES
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
