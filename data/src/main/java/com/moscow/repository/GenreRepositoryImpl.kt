package com.moscow.repository

import com.moscow.data_source.remote.GenreRemoteDataSource
import com.moscow.domain.model.Genre
import com.moscow.domain.repository.GenreRepository
import com.moscow.mapper.toDomain
import com.moscow.mapper.toEntity
import com.moscow.utils.GenreCacheHelper
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreRemoteDataSource: GenreRemoteDataSource,
    private val genreCacheHelper: GenreCacheHelper
) : GenreRepository {

    override suspend fun getMoviesGenres(forceRefresh: Boolean): List<Genre> =
        genreCacheHelper.getCachedOrFetchGenres(
            fetchFromRemote = { genreRemoteDataSource.getMoviesGenres().genres },
            mapToDomain = { it.toDomain() },
            mapToEntity = { it.toEntity() },
            forceRefresh = forceRefresh
        )

    override suspend fun getSeriesGenres(forceRefresh: Boolean): List<Genre> =
        genreCacheHelper.getCachedOrFetchGenres(
            fetchFromRemote = { genreRemoteDataSource.getSeriesGenres().genres },
            mapToDomain = { it.toDomain() },
            mapToEntity = { it.toEntity() },
            forceRefresh = forceRefresh
        )
}