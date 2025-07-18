package com.android.domain.repository

import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.model.SeriesDetail

interface DetailsRepository {
    suspend fun getMoviesDetail(movieId: Int): MovieDetail
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetail
    suspend fun getCreditsDetails(id: Int) : CreditsDetails
}