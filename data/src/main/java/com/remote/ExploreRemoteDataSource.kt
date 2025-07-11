package com.remote

import com.remote.dto.GenreDto
import com.remote.dto.GenreResponse
import com.utils.GENRE_MOVIE_LIST
import com.utils.GENRE_SERIES_LIST
import com.utils.performCall
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

class ExploreRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun getMoviesGenres(): List<GenreDto> =
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Get,
            path = GENRE_MOVIE_LIST
        ).genres

    suspend fun getSeriesGenres() =
        client.performCall<Unit, GenreResponse>(
            method = HttpMethod.Get,
            path = GENRE_SERIES_LIST
        ).genres


}
