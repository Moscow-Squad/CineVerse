package com.remote.data_source

import com.data_source.remote.GenreRemoteDataSource
import com.remote.dto.GenreResponse
import com.remote.services.GenreService
import com.utils.handleApi

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