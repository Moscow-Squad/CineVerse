package com.remote.source

import com.remote.dto.GenreDto
import com.remote.dto.GenreResponse
import com.remote.dto.MovieDto
import com.remote.dto.SeriesDto
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
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod

class ExploreRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun getMoviesGenres(): List<GenreDto> =
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Companion.Get,
            path = GENRE_MOVIE_LIST
        ).genres


    suspend fun getSeriesGenres(): List<GenreDto> =
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Companion.Get,
            path = GENRE_SERIES_LIST
        ).genres


    suspend fun getMovies(): List<MovieDto> =
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Get,
            path = MOVIE + POPULAR
        ) {
            parameter(PAGE, 1)
        }.results


    suspend fun getSeries(): List<SeriesDto> =
        client.performCall<Unit, ApiResponse<SeriesDto>>(
            method = HttpMethod.Get,
            path = SERIES + POPULAR
        ) {
            parameter(PAGE, 1)
        }.results

    suspend fun getSeriesByGenreId(genreId: Int): List<SeriesDto> =
        client.performCall<Unit, ApiResponse<SeriesDto>>(
            method = HttpMethod.Companion.Get,
            path = DISCOVER_SERIES_LIST,
            requestBuilder = {
                parameter(WITH_GENRES, genreId)
            }
        ).results


    suspend fun getMoviesByGenreId(genreId: Int): List<MovieDto> =
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Companion.Get,
            path = DISCOVER_MOVIE_LIST,
            requestBuilder = {
                parameter(WITH_GENRES, genreId)
            }
        ).results

}