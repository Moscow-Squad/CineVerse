package com.moscow.remote.services

import com.moscow.remote.dto.genre.GenreResponse
import com.moscow.utils.GENRE_MOVIE_LIST
import com.moscow.utils.GENRE_SERIES_LIST
import retrofit2.Response
import retrofit2.http.GET

interface GenreService {
    @GET(GENRE_MOVIE_LIST)
    suspend fun getMoviesGenres(): Response<GenreResponse>

    @GET(GENRE_SERIES_LIST)
    suspend fun getSeriesGenres(): Response<GenreResponse>
}
