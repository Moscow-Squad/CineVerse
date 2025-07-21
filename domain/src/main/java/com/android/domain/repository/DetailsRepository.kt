package com.android.domain.repository

import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.model.details.ListOfSeries
import com.android.domain.model.details.MovieDetail
import com.android.domain.model.details.Season
import com.android.domain.model.details.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getMoviesDetail(movieId: Int): MovieDetail
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetail
    suspend fun rateMovie(rating: Float, movieId: Int): Flow<Unit>
    suspend fun rateSeries(rating: Float, seriesId: Int): Flow<Unit>

    suspend fun getCreditsDetails(id: Int) : CreditsDetails
    suspend fun getLatestSeasons(): List<Season>
    suspend fun getListOfSeries(id: Int, page: Int): List<ListOfSeries>

}