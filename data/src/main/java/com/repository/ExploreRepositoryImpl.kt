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

}