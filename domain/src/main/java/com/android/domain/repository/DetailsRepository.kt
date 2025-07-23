package com.android.domain.repository

import com.android.domain.model.CreditsDetails
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail

interface DetailsRepository {
    suspend fun getMoviesDetail(movieId: Int): MovieDetail
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetail
    suspend fun rateMovie(rating: Float, movieId: Int)
    suspend fun rateSeries(rating: Float, seriesId: Int)
    suspend fun getCreditsDetails(id: Int) : CreditsDetails
    suspend fun getLatestSeasons(): List<Season>
    suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries>

}