package com.moscow.cineverse.screen.series_details.series_recommendation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.navigation.routes.SeriesRecommendationRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.model.Series
import com.moscow.domain.usecase.series.GetSeriesRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SeriesRecommendationViewModel @Inject constructor(
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    savedStateHandle: SavedStateHandle,
    ) : BaseViewModel<SeriesRecommendationScreenState, SeriesRecommendationEffects>(SeriesRecommendationScreenState()),
    SeriesRecommendationInteractionListener {
    val seriesId = savedStateHandle.get<Int>(SeriesRecommendationRoute.SERIES_ID) ?: 0
    val seriesName = savedStateHandle.get<String>(SeriesRecommendationRoute.SERIES_NAME) ?: ""

    fun getRecommendations(id: Int): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getSeriesRecommendationsUseCase(id, page)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    override fun onSeriesClicked(seriesId: Int) {
        sendEvent(SeriesRecommendationEffects.NavigateToSeriesDetailsScreen(seriesId))
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }
}