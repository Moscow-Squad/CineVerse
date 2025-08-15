package com.moscow.remote.services

import com.moscow.remote.dto.media_item.common.CreditsDetailsDto
import com.moscow.remote.dto.media_item.movie.MovieDto
import com.moscow.remote.dto.media_item.common.MediaTrailersDto
import com.moscow.remote.dto.media_item.movie.MovieDetailDto
import com.moscow.remote.dto.rating.response.UserRatingResponse
import com.moscow.remote.dto.rating.response.RatedMovieDto
import com.moscow.remote.dto.rating.request.RatingRequestDto
import com.moscow.remote.dto.review.ReviewDto
import com.moscow.utils.ACCOUNT
import com.moscow.utils.ACCOUNT_STATES
import com.moscow.utils.ApiResponse
import com.moscow.utils.CREDITS
import com.moscow.utils.DAY
import com.moscow.utils.DESCENDING
import com.moscow.utils.DISCOVER_MOVIE_LIST
import com.moscow.utils.MOVIE
import com.moscow.utils.NOW_PLAYING
import com.moscow.utils.PAGE
import com.moscow.utils.POPULAR
import com.moscow.utils.RATED_MOVIES
import com.moscow.utils.RATING
import com.moscow.utils.RECOMMENDATIONS
import com.moscow.utils.REVIEWS
import com.moscow.utils.SESSION_ID
import com.moscow.utils.SORTED_BY
import com.moscow.utils.TRAILERS
import com.moscow.utils.TRENDING
import com.moscow.utils.UPCOMING
import com.moscow.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    ): Response<Unit>

    @DELETE("$MOVIE{movie_id}$RATING")
    suspend fun deleteRatingMovie(
        @Path("movie_id") movieId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<Unit>

    @GET("$ACCOUNT/{account_id}$RATED_MOVIES")
    suspend fun getRatedMovies(
        @Path("account_id") id: Int,
        @Query(SESSION_ID) sessionId: String,
        @Query(PAGE) page: Int,
        @Query(SORTED_BY) sortBy: String = DESCENDING
    ): Response<ApiResponse<RatedMovieDto>>

    @GET("$MOVIE{movie_id}$ACCOUNT_STATES")
    suspend fun getUserRatingForMovie(
        @Path("movie_id") movieId: Int,
        @Query(SESSION_ID) sessionId: String
    ): Response<UserRatingResponse>

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

    @GET("$MOVIE{movie_id}$TRAILERS")
    suspend fun getMovieTrailers(
        @Path("movie_id") seriesId: Int
    ): Response<MediaTrailersDto>

    @GET("$TRENDING$MOVIE{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") time: String = DAY,
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<MovieDto>>

    @GET("$MOVIE$UPCOMING")
    suspend fun getUpComingMovies(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET("$MOVIE$NOW_PLAYING")
    suspend fun getRecentlyReleasedMovies(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>


    @GET(DISCOVER_MOVIE_LIST)
    suspend fun getMatchYourVibeMovies(
        @Query(WITH_GENRES) genreId: Int, @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>


    @GET(DISCOVER_MOVIE_LIST)
    suspend fun getMatchedMovies(
        @Query(PAGE) page: Int,
        @Query(WITH_GENRES) withGenres: String? = null,
        @Query("with_runtime.gte") runtimeGte: Int? = null,
        @Query("with_runtime.lte") runtimeLte: Int? = null,
        @Query("primary_release_date.gte") releaseDateGte: String? = null,
        @Query("primary_release_date.lte") releaseDateLte: String? = null,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<ApiResponse<MovieDto>>
}
