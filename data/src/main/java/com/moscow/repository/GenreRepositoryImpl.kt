package com.moscow.repository

import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.domain.model.Genre
import com.moscow.domain.repository.GenreRepository
import com.moscow.mapper.toDomain
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreRemoteDataSource: GenreRemoteDataSource,
) : GenreRepository {
    override suspend fun getSeriesGenres(): List<Genre> =
        genreRemoteDataSource.getSeriesGenres().genres.map { it.toDomain() }

    override suspend fun getMoviesGenres(): List<Genre> =
        genreRemoteDataSource.getMoviesGenres().genres.map { it.toDomain() }
}
