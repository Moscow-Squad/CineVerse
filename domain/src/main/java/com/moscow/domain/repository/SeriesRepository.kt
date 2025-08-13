package com.moscow.domain.repository

import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Review
import com.moscow.domain.model.Series
import com.moscow.domain.model.Series.Season
import com.moscow.domain.usecase.rating.GetRatedSeriesUseCase.RatedSeriesResult

interface SeriesRepository {
    suspend fun getPopularSeries(page: Int): List<Series>
    suspend fun getSeriesDetail(id: Int): Series
    suspend fun rateSeries(id: Int, rating: Float)
    suspend fun deleteRatingSeries(seriesId: Int)
    suspend fun getRatedSeries(userId: Int, page: Int): List<RatedSeriesResult>
    suspend fun getUserRatingForSeries(seriesId: Int) : Int
    suspend fun getLatestSeasons(): List<Season>
    suspend fun getListOfSeries(id: Int, page: Int): List<Series>
    suspend fun getSeriesRecommendations(id: Int, page: Int): List<Series>
    suspend fun getSeriesByGenreId(genreId: Int, page: Int): List<Series>
    suspend fun getTopRatedTVSeries(page: Int, forceRefresh: Boolean = false): List<Series>
    suspend fun getSeriesReviews(id: Int, page: Int): List<Review>
    suspend fun getSeriesCreditsDetails(id: Int): CreditsInfo
}