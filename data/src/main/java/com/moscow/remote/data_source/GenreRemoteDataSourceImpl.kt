package com.moscow.remote.data_source

import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.remote.dto.GenreResponse
import com.moscow.remote.services.GenreService
import com.moscow.utils.handleApi

class GenreRemoteDataSourceImpl(
    private val genreService: GenreService
) : GenreRemoteDataSource {
    override suspend fun getMoviesGenres(): GenreResponse =
        handleApi {
            genreService.getMoviesGenres()
        }

    override suspend fun getSeriesGenres(): GenreResponse =
        handleApi {
            genreService.getSeriesGenres()
        }
}