package com.mapper

import com.local.entity.FavouriteGenreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend fun Flow<List<FavouriteGenreEntity>>.toSortedGenres(): List<Int> {
    return this
        .first()
        .sortedByDescending { it.count }
        .map { it.genreId }
}