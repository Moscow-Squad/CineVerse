package com.moscow.cineverse.screen.series_details

import androidx.lifecycle.SavedStateHandle
import com.android.domain.model.Series
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.RateSeriesUseCase
import com.android.domain.usecase.seriesdetails.GetLatestSeasonsUseCase
import com.android.domain.usecase.seriesdetails.GetListOfSeriesUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesCreditsDetailsUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesDetailUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesRecommendationsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute

class SeriesDetailsViewModel(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val getSeriesCreditsDetailsUseCase: GetSeriesCreditsDetailsUseCase,
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    private val getLatestSeasonsUseCase: GetLatestSeasonsUseCase,
    private val getListOfSeriesUseCase: GetListOfSeriesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEvents>(SeriesDetailsScreenState()),
    SeriesInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        loadSeriesDetails(seriesId)
        loadSeriesCredits(seriesId)
        getSeriesRecommendations(seriesId)
        loadReviews(seriesId, page = 1)
    }

    fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail.ui(), isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(error = error.message, isLoading = false) }
            }
        )
    }

    fun loadSeriesCredits(seriesId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        launchWithResult(
            action = { getSeriesCreditsDetailsUseCase(seriesId) },
            onSuccess = { credits ->
                updateState {
                    it.copy(
                        cast = credits.actors.map { it.ui() },
                        crew = credits.behindTheScene.map { it.ui() },
                        isLoading = false
                    )
                }
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

    fun getSeriesRecommendations(seriesId: Int) {
        launchWithResult(
            action = { getSeriesRecommendationsUseCase(seriesId, 1) },
            onSuccess = ::onGetRecommendationsSuccess,
            onError = ::getRecommendationsFailed,
        )
    }

    private fun onGetRecommendationsSuccess(recommendations: List<Series>) {
        updateState { it.copy(recommendation = recommendations.take(6).map { it.ui() }) }
    }

    private fun getRecommendationsFailed(error: Throwable) {
        updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
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

    override fun showRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = true) }
    }

    fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    fun onRatingSubmit(rating: Int, seriesId: Int) {
        launchWithFlow(
            flowAction = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
            onSuccess = {
                updateState {
                    it.copy(
                        starsRating = rating,
                        showRatingBottomSheet = false
                    )
                }
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
        uiState.value.seriesDetail.let { sendEvent(SeriesDetailsEvents.AddToCollection(it.id)) }
    }
}