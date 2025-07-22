package com.moscow.cineverse.screen.series_details

import androidx.lifecycle.SavedStateHandle
import com.android.domain.model.Series
import com.android.domain.usecase.GetReviewsPageUseCase
import com.android.domain.usecase.RateSeriesUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesCreditsDetailsUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesDetailUseCase
import com.android.domain.usecase.seriesdetails.GetSeriesRecommendationsUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.screen.movie_details.toUi

class SeriesDetailsViewModel(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val getSeriesCreditsDetailsUseCase: GetSeriesCreditsDetailsUseCase,
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsScreenEvents>(SeriesDetailsScreenState()),
    SeriesInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        loadSeriesDetails(seriesId)
        loadSeriesCredits(seriesId)
        getSeriesRecommendations(seriesId, page = 1)
        loadReviews(seriesId, page = 1)
    }

    fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail.ui(), isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    fun loadSeriesCredits(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
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
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                updateState { it.copy(reviews = reviews.map { it.toUi() }, isLoading = false) }
            },
            onError = { error ->
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    fun getSeriesRecommendations(seriesId: Int, page: Int) {
        launchWithResult(
            action = { getSeriesRecommendationsUseCase(seriesId, page) },
            onSuccess = ::onGetRecommendationsSuccess,
            onError = ::getRecommendationsFailed,
        )
    }

    private fun onGetRecommendationsSuccess(recommendations: List<Series>) {
        updateState { it.copy(recommendation = recommendations.map { it.ui() }) }
    }

    private fun getRecommendationsFailed(error: Throwable) {
        updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
    }
    override fun showRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = true) }
    }

    override fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    override fun onRatingSubmit(rating: Int, seriesId: Int) {
        launchWithFlow(
            flowAction = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
            onSuccess = { updateState { it.copy(starsRating = rating, showRatingBottomSheet = false) } },
            onError = { updateState { it.copy(starsRating = rating, showRatingBottomSheet = false) } },
        )
    }

    override fun onShowMoreRecommendationsClicked(seriesId: Int) {
        sendEvent(SeriesDetailsScreenEvents.NavigateToRecommendationSeries(seriesId))
    }

    override fun onShowMoreReviewsClicked(seriesId: Int) {
        sendEvent(SeriesDetailsScreenEvents.NavigateToReviewsScreen(seriesId))
    }

    override fun addToCollection() {
        uiState.value.seriesDetail.let { sendEvent(SeriesDetailsScreenEvents.AddToCollection(it.id)) }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {
            it.copy(viewMode = viewMode)
        }

    }
}