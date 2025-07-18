package com.moscow.cineverse.screen.reviews


import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.domain.model.Review

import com.android.domain.usecase.GetReviewsPageUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.paging.MovieReviewsPagingSource

import kotlinx.coroutines.flow.Flow


class ReviewsViewModel(
    private val getReviewsPageUseCase: GetReviewsPageUseCase,

) : BaseViewModel<ReviewsScreenState, ReviewsScreenEvents>(ReviewsScreenState()),
    ReviewsInteractionListener {

    fun getPagedReviews(id: Int, isMovie: Boolean): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MovieReviewsPagingSource { page ->
                    getReviewsPageUseCase(id, page, isMovie)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }



    override fun onBackPressed() {
            sendEvent(ReviewsScreenEvents.NavigateBack)
    }
}