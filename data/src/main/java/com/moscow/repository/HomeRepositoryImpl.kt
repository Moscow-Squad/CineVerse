package com.moscow.repository


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
        const val CACHE_DURATION_MS = 60 * 60 * 1000
        const val CATEGORY_TRENDING = "TRENDING"
        const val CATEGORY_RECENTLY_RELEASED = "RECENTLY_RELEASED"
        const val CATEGORY_UPCOMING = "UPCOMING"
        const val CATEGORY_TOP_RATED_TV = "TOP_RATED_TV"
        const val CATEGORY_MATCHES_VIBE = "MATCHES_VIBE"
    }

    override suspend fun getTrendingMovies(time: String?): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_TRENDING,
            fetchFromRemote = {
                homeRemoteDataSource.getTrendingMovies(time).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() }
        )
    }

    override suspend fun getRecentlyReleasedMovies(page: Int): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_RECENTLY_RELEASED,
            fetchFromRemote = {
                homeRemoteDataSource.getRecentlyReleasedMovies(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() }
        )
    }

    override suspend fun getUpComingMovies(page: Int): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_UPCOMING,
            fetchFromRemote = {
                homeRemoteDataSource.getUpComingMovies(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() }
        )
    }

    override suspend fun getTopRatedTVSeries(page: Int): List<Series> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_TOP_RATED_TV,
            fetchFromRemote = {
                homeRemoteDataSource.getTopRatedTVSeries(page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toSeries() }
        )
    }

    override suspend fun getMatchYourVibeMovies(genreId: Int, page: Int): List<Movie> {
        return getCachedOrFetchHomeItems(
            categoryType = CATEGORY_MATCHES_VIBE,
            fetchFromRemote = {
                homeRemoteDataSource.getMatchYourVibeMovies(genreId, page).results?.map { it.toDomain() } ?: emptyList()
            },
            mapFromEntity = { it.toMovie() }
        )
    }

    private suspend inline fun <reified T> getCachedOrFetchHomeItems(
        categoryType: String,
        crossinline mapFromEntity: (HomeItemEntity) -> T,
        fetchFromRemote: suspend () -> List<T>
    ): List<T> {
        val timestamp = homeLocalDataSource.getCategoryTimestamp(categoryType)
        val isExpired = timestamp == null ||
                System.currentTimeMillis() - timestamp.lastRefreshed > CACHE_DURATION_MS

        if (isExpired) {
            val remoteData = fetchFromRemote()
            val entities = remoteData.map {
                when (it) {
                    is Movie -> it.toHomeItemEntity(categoryType)
                    is Series -> it.toHomeItemEntity(categoryType)
                    else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
                }
            }

            homeLocalDataSource.refreshHomeCategory(categoryType, entities)
        }

        return homeLocalDataSource.getHomeItemsByCategory(categoryType).map { mapFromEntity(it) }
    }
}