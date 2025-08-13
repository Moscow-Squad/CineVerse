package com.moscow.domain.repository

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import kotlinx.coroutines.flow.Flow

interface RecentlyViewedRepository {
    suspend fun addRecentlyViewedMovie(movie: Movie)
    suspend fun addRecentlyViewedSeries(series: Series)
    fun getRecentlyViewedMedia(): Flow<List<Any>> // Returns a mix of Movies and Series
    suspend fun deleteRecentlyViewedItemById(id: Int)
}