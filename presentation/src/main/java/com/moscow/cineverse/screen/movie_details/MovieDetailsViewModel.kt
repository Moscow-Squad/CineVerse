package com.moscow.cineverse.screen.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.domain.model.Review
import com.android.domain.usecase.GetMovieDetailUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.GetSeriesDetailUseCase
import com.moscow.cineverse.paging.MovieReviewsPagingSource
import kotlinx.coroutines.flow.Flow

class MovieDetailsViewModel(
    private val getSeriesDetailsUseCase: GetSeriesDetailUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase
) : ViewModel() {

    val reviewsFlow: Flow<PagingData<Review>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            MovieReviewsPagingSource { page -> getReviewsPageUseCase(24428, page, true) }
        }
    ).flow.cachedIn(viewModelScope)
}