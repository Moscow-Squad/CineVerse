package com.android.domain.repository

import com.android.domain.model.CreditsDetails
import com.android.domain.model.Review
import com.android.domain.model.Series
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail

interface SeriesRepository {
    suspend fun getPopularSeries(page: Int): List<Series>
    suspend fun getSeriesDetail(id: Int): SeriesDetail
    suspend fun rateSeries(id: Int, rating: Float)
    suspend fun getLatestSeasons(): List<Season>
    suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries>
    suspend fun getSeriesRecommendations(id: Int, page: Int): List<Series>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series>
    suspend fun getSeriesReviews(id: Int, page: Int): List<Review>
    suspend fun getSeriesCreditsDetails(id: Int): CreditsDetails
}