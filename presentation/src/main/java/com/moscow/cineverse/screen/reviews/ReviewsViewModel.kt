package com.moscow.cineverse.screen.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.ReviewsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.model.Review
import com.moscow.domain.usecase.review.GetReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getReviewsUseCase: GetReviewsUseCase,
    savedStateHandle: SavedStateHandle
    ) : BaseViewModel<ReviewsScreenState, ReviewsEffect>(ReviewsScreenState()),
    ReviewsInteractionListener {
    private val id: Int = savedStateHandle.get<Int>(ReviewsRoute.ID) ?: 0
    private val isMovie: Boolean = savedStateHandle.get<Boolean>(ReviewsRoute.IS_MOVIE) ?: false

    fun getPagedReviews(): Flow<PagingData<Review>> {
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