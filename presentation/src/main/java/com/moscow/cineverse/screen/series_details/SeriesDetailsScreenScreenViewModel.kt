package com.moscow.cineverse.screen.series_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.domain.model.Series
import com.moscow.domain.usecase.review.GetReviewsUseCase
import com.moscow.domain.usecase.series.GetSeriesCreditsDetailsUseCase
import com.moscow.domain.usecase.series.GetSeriesDetailUseCase
import com.moscow.domain.usecase.series.GetSeriesRecommendationsUseCase
import com.moscow.domain.usecase.series.RateSeriesUseCase
import kotlin.collections.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsScreenScreenViewModel @Inject constructor(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val getSeriesCreditsDetailsUseCase: GetSeriesCreditsDetailsUseCase,
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsScreenEffects>(SeriesDetailsScreenState()),
    SeriesDetailsScreenInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        updateState { it.copy(isLoading = true) }
        loadSeriesDetails(seriesId)
        loadSeriesCredits(seriesId)
        getSeriesRecommendations(seriesId, page = 1)
        loadReviews(seriesId, page = 1)
        updateState { it.copy(isLoading = false) }
    }

    private fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail.toUi(), isLoading = false) }
            },
            onError = { error ->
                Log.d("ddddddddddd", error.message.toString())
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    private fun loadSeriesCredits(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getSeriesCreditsDetailsUseCase(seriesId) },
            onSuccess = { credits ->
                updateState {
                    it.copy(
                        cast = credits.actors.map { it.toUi() },
                        crew = credits.behindTheScene.map { it.toUi() },
                        isLoading = false
                    )
                }
            },
            onError = { error ->
                Log.d("ddddddddddd", error.message.toString())
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    private fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                updateState { it.copy(reviews = reviews.map { it.toUi() }, isLoading = false) }
            },
            onError = { error ->
                Log.d("ddddddddddd", error.message.toString())
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    private fun getSeriesRecommendations(seriesId: Int, page: Int) {
        launchWithResult(
            action = { getSeriesRecommendationsUseCase(seriesId, page) },
            onSuccess = ::onGetRecommendationsSuccess,
            onError = ::getRecommendationsFailed,
        )
    }

    private fun onGetRecommendationsSuccess(recommendations: List<Series>) {
        updateState { it.copy(recommendation = recommendations.map { it.toUi() }) }
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
        launchAndForget(
            action = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
            onSuccess = { updateState { it.copy(starsRating = rating, showRatingBottomSheet = false) } },
            onError = { updateState { it.copy(starsRating = rating, showRatingBottomSheet = false) } },
        )
    }

    override fun onShowMoreRecommendationsClicked(seriesId: Int, seriesName: String) {
        sendEvent(SeriesDetailsScreenEffects.NavigateToRecommendationSeries(seriesId, seriesName))
    }

    override fun onShowMoreReviewsClicked(seriesId: Int) {
        sendEvent(SeriesDetailsScreenEffects.NavigateToReviewsScreen(seriesId))
    }

    override fun onShowMoreSeasonsClicked(seriesId: Int) {
        sendEvent(SeriesDetailsScreenEffects.NavigateToSeriesSeasonsScreen(seriesId))
    }

    override fun addToCollection() {
        uiState.value.seriesDetail.let { sendEvent(SeriesDetailsScreenEffects.AddToCollection(it.id)) }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }

    override fun onSeriesClicked(seriesId: Int) {
        sendEvent(SeriesDetailsScreenEffects.NavigateToSeriesDetailsScreen(seriesId))
    }

    override fun onActorClicked(actorId: Int) {
        sendEvent(SeriesDetailsScreenEffects.NavigateToActorDetailsScreen(actorId))
    }
}