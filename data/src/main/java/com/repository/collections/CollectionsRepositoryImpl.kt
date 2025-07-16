package com.repository.collections

import com.android.domain.model.Collection
import com.android.domain.repository.CollectionsRepository
import com.remote.dto.toDomain
import com.remote.source.CollectionsDataSource
import com.utils.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CollectionsRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val collectionsDataSource: CollectionsDataSource
) : CollectionsRepository, BaseRepository() {
    override suspend fun getCollections(): Flow<List<Collection>> =
        tryToExecute {
            flow {
                val response = collectionsDataSource.getMyCollections(null)
                emit(response.map { it.toDomain() })
            }.flowOn(ioDispatcher)
        }
}