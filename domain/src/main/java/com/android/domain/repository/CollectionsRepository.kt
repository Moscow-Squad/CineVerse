package com.android.domain.repository

import com.android.domain.model.Collection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    suspend fun getCollections(): Flow<List<Collection>>
}