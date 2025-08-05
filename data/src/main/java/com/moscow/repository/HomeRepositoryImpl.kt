package com.moscow.repository


import android.util.Log
import com.moscow.data_source.remote.HomeRemoteDataSource
import com.moscow.data_source.local.HomeLocalDataSource
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.repository.HomeRepository
import com.moscow.local.entity.HomeItemEntity
import com.moscow.mapper.toDomain
import com.moscow.mapper.toHomeItemEntity
import com.moscow.mapper.toMovie
import com.moscow.mapper.toSeries
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource : HomeRemoteDataSource,
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {

    companion object {
        const val CACHE_DURATION_MS = 24 * 60 * 60 * 1000
        const val CATEGORY_TRENDING = "TRENDING"
        const val CATEGORY_RECENTLY_RELEASED = "RECENTLY_RELEASED"
        const val CATEGORY_UPCOMING = "UPCOMING"
        const val CATEGORY_TOP_RATED_TV = "TOP_RATED_TV"
        const val CATEGORY_MATCHES_VIBE = "MATCHES_VIBE"
    }

    override suspend fun getTrendingMovies(time: String?, forceRefresh: Boolean): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_TRENDING,
            fetchFromRemote = {
                homeRemoteDataSource.getTrendingMovies(time).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getRecentlyReleasedMovies(page: Int, forceRefresh: Boolean): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_RECENTLY_RELEASED,
            fetchFromRemote = {
                homeRemoteDataSource.getRecentlyReleasedMovies(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        ).also { Log.d("TAGE", it.size.toString()) }
    }

    override suspend fun getUpComingMovies(page: Int, forceRefresh: Boolean): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_UPCOMING,
            fetchFromRemote = {
                homeRemoteDataSource.getUpComingMovies(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getTopRatedTVSeries(page: Int, forceRefresh: Boolean): List<Series> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_TOP_RATED_TV,
            fetchFromRemote = {
                homeRemoteDataSource.getTopRatedTVSeries(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toSeries() },
            forceRefresh = forceRefresh
        )
    }

    override suspend fun getMatchYourVibeMovies(genreId: Int, page: Int, forceRefresh: Boolean): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_MATCHES_VIBE,
            fetchFromRemote = {
                homeRemoteDataSource.getMatchYourVibeMovies(genreId, page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() },
            forceRefresh = forceRefresh
        )
    }

    private suspend inline fun <reified T> getCachedOrFetchHomeItems(
        categoryType: String,
        crossinline mapFromEntity: (HomeItemEntity) -> T,
        fetchFromRemote: suspend () -> List<T>,
        forceRefresh: Boolean = false,
        forceCache: Boolean = false
    ): List<T> {
        if (forceCache) {
            return homeLocalDataSource.getHomeItemsByCategory(categoryType).map { mapFromEntity(it) }
        }

        val timestamp = homeLocalDataSource.getCategoryTimestamp(categoryType)
        val isExpired = timestamp == null ||
                System.currentTimeMillis() - timestamp.lastRefreshed > CACHE_DURATION_MS

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