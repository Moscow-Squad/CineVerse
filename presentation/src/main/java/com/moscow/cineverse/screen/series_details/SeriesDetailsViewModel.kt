package com.moscow.cineverse.screen.series_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.seriesdetails.GetLatestSeasonsUseCase
import com.android.domain.usecase.seriesdetails.GetListOfSeriesUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesDetailUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute

class SeriesDetailsViewModel(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
    private val getLatestSeasonsUseCase: GetLatestSeasonsUseCase,
    private val getListOfSeriesUseCase: GetListOfSeriesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<SeriesDetailsUiState, SeriesDetailsEvents>(SeriesDetailsUiState()),
    SeriesInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        loadSeriesDetails(seriesId)
        loadReviews(seriesId, page = 1)
    }
    fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail, isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                updateState { it.copy(reviews = reviews, isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    fun latestSeasons() {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getLatestSeasonsUseCase() },
            onSuccess = { _ ->
                updateState { it.copy(isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )

    }
    fun listOfSeries(id: Int, page: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getListOfSeriesUseCase(id, page) },
            onSuccess = { _ ->
                updateState { it.copy(isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    override fun addToCollection() {
        Log.e("kllvmv", "addToCollection: ${uiState.value.seriesDetail?.id}")
        uiState.value.seriesDetail?.let { sendEvent(SeriesDetailsEvents.AddToCollection(it.id)) }
    }
}