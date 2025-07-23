package com.moscow.cineverse.screen.series_details

import androidx.lifecycle.SavedStateHandle
import com.android.domain.usecase.review.GetReviewsUseCase
import com.android.domain.usecase.series.RateSeriesUseCase
import com.android.domain.usecase.series.GetLatestSeasonsUseCase
import com.android.domain.usecase.series.GetListOfSeriesUseCase
import com.android.domain.usecase.series.GetSeriesDetailUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute

class SeriesDetailsViewModel(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getLatestSeasonsUseCase: GetLatestSeasonsUseCase,
    private val getListOfSeriesUseCase: GetListOfSeriesUseCase,
    savedStateHandle: SavedStateHandle,
    private val rateSeriesUseCase : RateSeriesUseCase
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
            action = { getReviewsUseCase(seriesId, page, isMovie = false) },
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

    fun showRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = true) }
    }

    fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    fun onRatingSubmit(rating: Int, seriesId: Int) {
       launchWithResult(
           action = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
           onSuccess = {
               updateState { it.copy(
                   starsRating = rating,
                   showRatingBottomSheet = false
               ) }
           },
           onError = {
               updateState {
                   it.copy(
                   starsRating = rating,
                   showRatingBottomSheet = false
                   )
               }
           },
       )
    }


    override fun addToCollection() {
        uiState.value.seriesDetail?.let { sendEvent(SeriesDetailsEvents.AddToCollection(it.id)) }
    }
}