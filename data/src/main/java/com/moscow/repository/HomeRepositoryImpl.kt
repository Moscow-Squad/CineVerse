package com.moscow.repository


import com.moscow.data_source.local.HomeLocalDataSource
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.repository.HomeRepository
import com.moscow.local.entity.MediaItemEntity
import com.moscow.mapper.toHomeItemEntity
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {

    companion object {
        const val CACHE_DURATION_MS = 24 * 60 * 60 * 1000
        const val CATEGORY_MATCHES_VIBE = "MATCHES_VIBE"
    }


    override suspend fun clearHomeCash() {
        homeLocalDataSource.clearHomeCash()
    }

    private suspend inline fun <reified T> getCachedOrFetchHomeItems(
        categoryType: String,
        crossinline mapFromEntity: (MediaItemEntity) -> T,
        fetchFromRemote: suspend () -> List<T>,
        forceRefresh: Boolean = false,
        forceCache: Boolean = false
    ): List<T> {
        if (forceCache) {
            return homeLocalDataSource.getHomeItemsByCategory(categoryType).map { mapFromEntity(it) }
        }

        val timestamp = homeLocalDataSource.getCategoryTimestamp(categoryType)
        val isExpired = timestamp == null || System.currentTimeMillis() - timestamp.lastRefreshed > CACHE_DURATION_MS

        if(forceRefresh || isExpired) {
            val remoteData = fetchFromRemote()
            val entities = remoteData.map {
                when (it) {
                    is Movie -> it.toHomeItemEntity(categoryType)
                    is Series -> it.toHomeItemEntity(categoryType)
                    else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
                }
            }

            if (forceRefresh) {
                return entities.map { mapFromEntity(it) }
            }

            homeLocalDataSource.refreshHomeCategory(categoryType, entities)
            return remoteData
        }

        return homeLocalDataSource.getHomeItemsByCategory(categoryType).map { mapFromEntity(it) }
    }
}