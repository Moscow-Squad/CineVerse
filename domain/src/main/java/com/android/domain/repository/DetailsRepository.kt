package com.android.domain.repository

import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.model.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getMoviesDetail(movieId: Int): MovieDetail
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetail
    suspend fun getReviewsPage(id: Int, page: Int, isMovie: Boolean): List<Review>
    suspend fun rateMovie(rating: Float, movieId: Int): Flow<Unit>
    suspend fun rateSeries(rating: Float, seriesId: Int): Flow<Unit>

}