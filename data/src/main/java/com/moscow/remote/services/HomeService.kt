package com.remote.services


import com.moscow.remote.dto.MovieDto
import com.moscow.remote.dto.series.SeriesDto
import com.moscow.utils.ApiResponse
import com.moscow.utils.DISCOVER_MOVIE_LIST
import com.moscow.utils.MOVIE
import com.moscow.utils.NOW_PLAYING
import com.moscow.utils.PAGE
import com.moscow.utils.SERIES
import com.moscow.utils.TOP_RATED
import com.moscow.utils.TRENDING
import com.moscow.utils.UPCOMING
import com.moscow.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("$TRENDING$MOVIE{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") time: String,
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

    @GET("$SERIES$TOP_RATED")
    suspend fun getTopRatedTVSeries(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<SeriesDto>>


}