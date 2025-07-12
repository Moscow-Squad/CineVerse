package com.repository.explore

import com.android.domain.model.Genre
import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.remote.ExploreRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExploreRepositoryImpl(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ExploreRepository {
    override suspend fun getSeriesGenres(): Flow<List<Genre>> =
        flow {
            val genres = exploreRemoteDataSource.getSeriesGenres()
            emit(genres.map { it.toDomain() })
        }.flowOn(ioDispatcher)

    override suspend fun getMoviesGenres(): Flow<List<Genre>> =
        flow {
            val genres = exploreRemoteDataSource.getMoviesGenres()
            emit(genres.map { it.toDomain() })
        }.flowOn(ioDispatcher)

    override suspend fun geMovies(): Flow<List<Movie>> = flow {
        emit(exploreRemoteDataSource.getMovies().results.map { dto -> dto.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun getSeries(): Flow<List<Series>> = flow {
        emit(exploreRemoteDataSource.getSeries().results.map { dto -> dto.toDomain() })
    }.flowOn(ioDispatcher)
}