package com.remote.services

import retrofit2.http.*
import com.remote.dto.*
import com.remote.dto.details.*
import com.utils.ApiResponse
import com.utils.DISCOVER_MOVIE_LIST
import com.utils.DISCOVER_SERIES_LIST
import com.utils.GENRE_MOVIE_LIST
import com.utils.GENRE_SERIES_LIST
import com.utils.MOVIE
import com.utils.PAGE
import com.utils.POPULAR
import com.utils.SERIES
import com.utils.WITH_GENRES
import retrofit2.Response

interface ExploreService {

    @GET(GENRE_MOVIE_LIST)
    suspend fun getMoviesGenres()
    : Response<GenreResponse>

    @GET(GENRE_SERIES_LIST)
    suspend fun getSeriesGenres()
    : Response<GenreResponse>

    @GET("$MOVIE$POPULAR")
    suspend fun getMovies(
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<MovieDto>>

    @GET("$SERIES$POPULAR")
    suspend fun getSeries(
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<SeriesDto>>

    @GET(DISCOVER_SERIES_LIST)
    suspend fun getSeriesByGenreId(
        @Query(WITH_GENRES) genreId: Int,
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<SeriesDto>>

    @GET(DISCOVER_MOVIE_LIST)
    suspend fun getMoviesByGenreId(
        @Query(WITH_GENRES) genreId: Int,
        @Query(PAGE) page: Int = 1
    ): Response<ApiResponse<MovieDto>>
}