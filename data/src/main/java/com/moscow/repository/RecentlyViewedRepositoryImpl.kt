package com.moscow.repository

import com.moscow.data_source.local.RecentlyViewedLocalDataSource
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.repository.RecentlyViewedRepository
import com.moscow.mapper.ITEM_MOVIE
import com.moscow.mapper.ITEM_SERIES
import com.moscow.mapper.toHistoryItemEntity
import com.moscow.mapper.toMovie
import com.moscow.mapper.toSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentlyViewedRepositoryImpl @Inject constructor(
    private val recentlyViewedLocalDataSource: RecentlyViewedLocalDataSource
) : RecentlyViewedRepository {

    override suspend fun addRecentlyViewedMovie(movie: Movie) {
        val entity = movie.toHistoryItemEntity()
        recentlyViewedLocalDataSource.insertRecentlyViewedItem(entity)
    }

    override suspend fun addRecentlyViewedSeries(series: Series) {
        val entity = series.toHistoryItemEntity()
        recentlyViewedLocalDataSource.insertRecentlyViewedItem(entity)
    }

    override suspend fun getRecentlyViewedMedia(): Flow<List<Any>> {
        val entities = recentlyViewedLocalDataSource.getRecentlyViewedItems()
        return entities.map {
            it.map{entity ->
                when (entity.itemType) {
                    ITEM_MOVIE -> entity.toMovie()
                    ITEM_SERIES -> entity.toSeries()
                    else -> throw IllegalArgumentException("Unknown media type: ${entity.itemType}")
                }
            }
        }
    }

    override suspend fun deleteRecentlyViewedItemById(
        id: Int
    ) = recentlyViewedLocalDataSource.deleteRecentlyViewedItemById(id = id)
}