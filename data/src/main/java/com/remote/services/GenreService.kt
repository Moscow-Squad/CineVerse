package com.remote.services

import com.remote.dto.GenreResponse
import com.utils.GENRE_MOVIE_LIST
import com.utils.GENRE_SERIES_LIST
import retrofit2.Response
import retrofit2.http.GET

interface GenreService {
    @GET(GENRE_MOVIE_LIST)
    suspend fun getMoviesGenres(): Response<GenreResponse>

    @GET(GENRE_SERIES_LIST)
    suspend fun getSeriesGenres(): Response<GenreResponse>
}