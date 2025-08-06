package com.moscow.domain.repository

import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series

interface RecentlyViewedRepository {
    suspend fun addRecentlyViewedMovie(movie: Movie)
    suspend fun addRecentlyViewedSeries(series: Series)
    suspend fun getRecentlyViewedMedia(): List<Any> // Returns a mix of Movies and Series
}