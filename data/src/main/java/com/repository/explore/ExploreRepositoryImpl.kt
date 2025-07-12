package com.repository.explore

import com.android.domain.model.Genre
import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.mapper.toModel
import com.remote.ExploreRemoteDataSource
import com.remote.mapper.MovieMapper
import com.remote.source.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExploreRepositoryImpl(
    private val exploreRemoteDataSource: ExploreRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapper: MovieMapper
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

    override suspend fun geMovies(): List<Movie> = exploreRemoteDataSource.getMovies().results.map { dto ->
        val details = exploreRemoteDataSource.getMovieDetails(dto.id?:0)
        mapper.mapToMovie(dto,details)
    }
    override suspend fun getSeries(): List<Series> = exploreRemoteDataSource.getSeries().results.map { dto ->
        val details = exploreRemoteDataSource.getSeriesDetails(dto.id?:0)
        mapper.mapToSeries(dto, details)
    }
}