package com.moscow.data_source.local

import com.moscow.local.entity.GenreEntity

interface GenreLocalDataSource {
    suspend fun getAllGenres(): List<GenreEntity>
    suspend fun insertGenres(genres: List<GenreEntity>)
    suspend fun clearGenres()
}