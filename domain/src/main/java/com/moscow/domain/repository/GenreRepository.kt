package com.moscow.domain.repository

import com.moscow.domain.model.Genre
interface GenreRepository {
    suspend fun getSeriesGenres(forceRefresh: Boolean = false): List<Genre>
    suspend fun getMoviesGenres(forceRefresh: Boolean = false): List<Genre>
}