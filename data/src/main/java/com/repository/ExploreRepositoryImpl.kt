package com.repository

import com.android.domain.exception.CineVerseException
import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import com.android.domain.repository.ExploreRepository
import com.mapper.toDomain
import com.remote.source.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExploreRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,

    ) : ExploreRepository {
    override suspend fun searchMulti(
        query: String,
    ): Flow<List<MultiSearch>> =
        flow {
            val result = searchRemoteDataSource.searchMulti(query)
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)


    override suspend fun searchMovie(query: String): Flow<List<Movie>> =
        flow {
            val result = searchRemoteDataSource.searchMovie(query)
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)


    override suspend fun searchSeries(query: String): Flow<List<Series>> =
        flow {
            val result = searchRemoteDataSource.searchSeries(query)
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)

    override suspend fun searchActor(query: String): Flow<List<Actor>> =
        flow {
            val result = searchRemoteDataSource.searchPearson(query)
            if (result.isNotEmpty()) {
                emit(result.map { it.toDomain() })
            } else {
                throw CineVerseException.NotFoundCineVerseException
            }
        }.flowOn(ioDispatcher)
}