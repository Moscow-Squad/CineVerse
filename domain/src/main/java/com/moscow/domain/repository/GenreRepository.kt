package com.moscow.domain.repository

import com.moscow.domain.model.Genre

interface GenreRepository {
    suspend fun getSeriesGenres():List<Genre>
    suspend fun getMoviesGenres(): List<Genre>
}