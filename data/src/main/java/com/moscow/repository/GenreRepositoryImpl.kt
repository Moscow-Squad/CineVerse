package com.moscow.repository

import com.android.domain.model.Genre
import com.android.domain.repository.GenreRepository
import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.mapper.toDomain

class GenreRepositoryImpl(
    private val genreRemoteDataSource: GenreRemoteDataSource,
): GenreRepository {
    override suspend fun getSeriesGenres(): List<Genre> {
        return genreRemoteDataSource.getSeriesGenres().genres.map { it.toDomain() }
    }

    override suspend fun getMoviesGenres(): List<Genre> {
        return genreRemoteDataSource.getMoviesGenres().genres.map { it.toDomain() }
    }
}
