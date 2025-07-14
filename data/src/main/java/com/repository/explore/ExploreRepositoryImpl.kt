package com.repository.explore

import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.remote.ExploreRemoteDataSource
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExploreRepositoryImpl(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ExploreRepository, BaseRepository() {
    override suspend fun getSeriesGenres(): Flow<List<Genre>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getSeriesGenres() }.map { it.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun getMoviesGenres(): Flow<List<Genre>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getMoviesGenres() }.map { it.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun geMovies(): Flow<List<Movie>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getMovies() }.results.map { it.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun getSeries(): Flow<List<Series>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getSeries() }.results.map { it.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun getMoviesByGenreId(genreId: Int): Flow<List<Movie>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getMoviesByGenreId(genreId) }.map { it.toDomain() })
    }.flowOn(ioDispatcher)

    override suspend fun getSeriesByGenreId(genreId: Int): Flow<List<Series>> = flow {
        emit(tryToExecute { exploreRemoteDataSource.getSeriesByGenreId(genreId) }.map { it.toDomain() })
    }.flowOn(ioDispatcher)
}