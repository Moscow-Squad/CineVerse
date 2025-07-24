package com.moscow.cineverse.screen.reviews

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.domain.model.Review
import com.android.domain.usecase.review.GetReviewsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.paging.BasePagingSource
import kotlinx.coroutines.flow.Flow

class ReviewsViewModel(
    private val getReviewsUseCase: GetReviewsUseCase
    ) : BaseViewModel<ReviewsScreenState, ReviewsEffect>(ReviewsScreenState()),
    ReviewsInteractionListener {

    fun getPagedReviews(id: Int, isMovie: Boolean): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getReviewsUseCase(id, page, isMovie)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    override fun onBackPressed() {
            sendEvent(ReviewsEffect.NavigateBack)
    }
}