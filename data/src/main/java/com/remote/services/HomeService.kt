package com.remote.services

import com.android.domain.model.Movie
import com.remote.dto.MovieDto
import com.remote.dto.series.SeriesDto
import com.utils.ApiResponse
import com.utils.DISCOVER_MOVIE_LIST
import com.utils.MOVIE
import com.utils.NOW_PLAYING
import com.utils.PAGE
import com.utils.SERIES
import com.utils.TOP_RATED
import com.utils.TRENDING
import com.utils.UPCOMING
import com.utils.WITH_GENRES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("$TRENDING$MOVIE{time_window}")
    fun getTrendingMovies(
        @Path("time_window") time:String,
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<MovieDto>>

    @GET("$MOVIE$UPCOMING")
    fun getUpComingMovies(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET("$MOVIE$NOW_PLAYING")
    fun getRecentlyReleasedMovies(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>


    @GET(DISCOVER_MOVIE_LIST)
    fun getMatchYourVibeMovies(
        @Query(WITH_GENRES) genreId: Int, @Query(PAGE) page: Int
    ): Response<ApiResponse<MovieDto>>

    @GET("$SERIES$TOP_RATED")
    fun getTopRatedTVSeries(
        @Query(PAGE) page: Int
    ): Response<ApiResponse<SeriesDto>>


}