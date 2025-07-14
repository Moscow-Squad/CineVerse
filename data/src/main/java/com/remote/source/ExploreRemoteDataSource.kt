package com.remote.source

import com.remote.dto.GenreDto
import com.remote.dto.GenreResponse
import com.remote.dto.MovieDetailsDto
import com.remote.dto.MovieDto
import com.remote.dto.SeriesDto
import com.utils.ApiResponse
import com.utils.BaseRepository
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
) : BaseRepository() {
    suspend fun getMoviesGenres(): List<GenreDto> = tryToExecute {
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Companion.Get,
            path = GENRE_MOVIE_LIST
        ).genres
    }

    suspend fun getSeriesGenres(): List<GenreDto> = tryToExecute {
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Companion.Get,
            path = GENRE_SERIES_LIST
        ).genres
    }


    suspend fun getMovies(): List<MovieDto> =
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Get,
            path = MOVIE + POPULAR
        ) {
            parameter(PAGE, 1)
        }.results


    suspend fun getSeries(): List<SeriesDto> =
        tryToExecute {
            client.performCall<Unit, ApiResponse<SeriesDto>>(
                method = HttpMethod.Get,
                path = SERIES + POPULAR
            ) {
                parameter(PAGE, 1)
            }.results
        }

    suspend fun getMovieDetails(id: Int): MovieDetailsDto = tryToExecute {
        client.performCall<Unit, MovieDetailsDto>(
            method = HttpMethod.Get,
            path = MOVIE + id
        )
    }


//    suspend fun getSeries(): PopularResponse<SeriesDto> = tryToExecute {
//        client.get("https://api.themoviedb.org/3/tv/popular") {
//            parameter("language", "en-US")
//            parameter("page", 1)
//        }.body()
//    }


    suspend fun getSeriesDetails(id: Int): SeriesDto =
        tryToExecute {
            client.performCall<Unit, SeriesDto>(
                method = HttpMethod.Get,
                path = SERIES + id
            )
        }

    suspend fun getSeriesByGenreId(genreId: Int): List<SeriesDto> =
        tryToExecute {
            client.performCall<Unit, ApiResponse<SeriesDto>>(
                method = HttpMethod.Companion.Get,
                path = DISCOVER_SERIES_LIST,
                requestBuilder = {
                    parameter(WITH_GENRES, genreId)
                }
            ).results
        }


    suspend fun getMoviesByGenreId(genreId: Int): List<MovieDto> = tryToExecute {
        client.performCall<Unit, ApiResponse<MovieDto>>(
            method = HttpMethod.Companion.Get,
            path = DISCOVER_MOVIE_LIST,
            requestBuilder = {
                parameter(WITH_GENRES, genreId)
            }
        ).results
    }
}