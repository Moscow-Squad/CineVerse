package com.android.domain.repository

import com.android.domain.model.Genre

interface GenreRepository {
    suspend fun getSeriesGenres():List<Genre>
    suspend fun getMoviesGenres(): List<Genre>
}