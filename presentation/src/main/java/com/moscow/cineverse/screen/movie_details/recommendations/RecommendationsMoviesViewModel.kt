package com.moscow.cineverse.screen.movie_details.recommendations

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.domain.model.Movie
import com.android.domain.usecase.GetRecommendationsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.paging.BasePagingSource
import kotlinx.coroutines.flow.Flow

class RecommendationsMoviesViewModel(
    private val getRecommendationsUseCase: GetRecommendationsUseCase
) : BaseViewModel<RecommendationsMoviesState,
        RecommendationMoviesEffect>(RecommendationsMoviesState()),
    RecommendationsMoviesInteractionListener {

    fun getRecommendations(id: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getRecommendationsUseCase(id, page)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {
            it.copy(viewMode = viewMode)
        }
    }

    override fun onMovieClick(movieId: Int) {

    }

    override fun backButtonClick() {
        sendEvent(RecommendationMoviesEffect.NavigateBack)
    }
}