package com.data_source.remote

import com.remote.dto.GenreResponse

interface GenreRemoteDataSource {
    suspend fun getMoviesGenres(): GenreResponse
    suspend fun getSeriesGenres(): GenreResponse
}