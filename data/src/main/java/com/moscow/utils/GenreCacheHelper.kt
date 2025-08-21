package com.moscow.utils

import com.moscow.data_source.local.GenreLocalDataSource
import com.moscow.domain.model.Genre
import com.moscow.local.entity.GenreEntity
import com.moscow.mapper.toDomain
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GenreCacheHelper @Inject constructor(
    private val genreLocalDataSource: GenreLocalDataSource
) {
    suspend fun <T> getCachedOrFetchGenres(
        fetchFromRemote: suspend () -> List<T>,
        mapToDomain: (T) -> Genre,
        mapToEntity: (T) -> GenreEntity,
        forceRefresh: Boolean,
        cacheDuration: Long = TimeUnit.DAYS.toMillis(7)
    ): List<Genre> {
        val currentTime = System.currentTimeMillis()
        val cachedGenres = genreLocalDataSource.getAllGenres()

        return if (cachedGenres.isEmpty() || forceRefresh ||
            (currentTime - (cachedGenres.firstOrNull()?.timestamp ?: 0)) > cacheDuration) {

            val remoteGenres = fetchFromRemote()

            val genreEntities = remoteGenres.map(mapToEntity)
            genreLocalDataSource.insertGenres(genreEntities)

            remoteGenres.map(mapToDomain)
        } else {
            cachedGenres.map { it.toDomain() }
        }
    }
}