package com.android.domain.repository

import com.android.domain.model.Genre
import kotlinx.coroutines.flow.Flow


interface ExploreRepository {
    suspend fun getSeriesGenres(): Flow<List<Genre>>
    suspend fun getMoviesGenres(): Flow<List<Genre>>

}