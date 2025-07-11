package com.android.domain.repository

import com.android.domain.model.Actor
import com.android.domain.model.Movie
import com.android.domain.model.MultiSearch
import com.android.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    suspend fun searchMulti(
        query: String,
    ): Flow<List<MultiSearch>>

    suspend fun searchMovie(
        query: String,
    ): Flow<List<Movie>>

    suspend fun searchSeries(
        query: String,
    ): Flow<List<Series>>

    suspend fun searchActor(
        query: String,
    ): Flow<List<Actor>>

}