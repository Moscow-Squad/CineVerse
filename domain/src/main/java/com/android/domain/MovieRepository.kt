package com.android.domain

import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series

interface MovieRepository {
    suspend fun geMovies(): List<Movie>
    suspend fun getSeries(): List<Series>
}