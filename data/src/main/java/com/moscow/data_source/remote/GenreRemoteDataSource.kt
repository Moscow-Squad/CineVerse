package com.moscow.data_source.remote

import com.moscow.remote.dto.GenreResponse

interface GenreRemoteDataSource {
    suspend fun getMoviesGenres(): GenreResponse
    suspend fun getSeriesGenres(): GenreResponse
}