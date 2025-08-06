package com.moscow.cineverse.screen.series_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.domain.model.Series
import com.moscow.domain.repository.PreferenceRepository
import com.moscow.domain.usecase.review.GetReviewsUseCase
import com.moscow.domain.usecase.series.DeleteRatingSeriesUseCase
import com.moscow.domain.usecase.series.GetSeriesCreditsDetailsUseCase
import com.moscow.domain.usecase.series.GetSeriesDetailUseCase
import com.moscow.domain.usecase.series.GetSeriesRecommendationsUseCase
import com.moscow.domain.usecase.series.GetUserRatingForSeriesUseCase
import com.moscow.domain.usecase.series.RateSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsScreenScreenViewModel @Inject constructor(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val getSeriesCreditsDetailsUseCase: GetSeriesCreditsDetailsUseCase,
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    private val deleteRatingSeriesUseCase: DeleteRatingSeriesUseCase,
    private val getUserRatingForSeriesUseCase: GetUserRatingForSeriesUseCase,
    private val preferences: PreferenceRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsScreenEffects>(SeriesDetailsScreenState()),
    SeriesDetailsScreenInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            getUserRating(seriesId)
            loadSeriesDetails(seriesId)
            loadSeriesCredits(seriesId)
            getSeriesRecommendations(seriesId, page = 1)
            loadReviews(seriesId, page = 1)
            waitUntilAllDataIsReady()
            updateState { it.copy(isLoading = false) }
        }
    }

    private fun getUserRating(seriesId: Int) {
        launchWithResult(
            action = { getUserRatingForSeriesUseCase.invoke(seriesId) },
            onSuccess = { rate ->
                updateState { it.copy(starsRating = rate) }
            },
            onError = {
                updateState { it.copy(starsRating = 0) }
            }
        )
    }

    private suspend fun waitUntilAllDataIsReady() {
        var wait = 0
        while (uiState.value.seriesDetail.id == 0) {
            wait++
            if (wait == 25){
                updateState { it.copy(isLoading = false, errorMessage = "error loading", shouldShowError = true) }
                return
            }
            delay(100)
        }
    }

    private fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getSeriesDetailUseCase(seriesId) },
            onSuccess = { detail ->
                updateState { it.copy(seriesDetail = detail.toUi()) }
            },
            onError = { error ->
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    private fun loadSeriesCredits(seriesId: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getSeriesCreditsDetailsUseCase(seriesId) },
            onSuccess = { credits ->
                val crew = credits.behindTheScene.map { it.toUi() }
                updateState {
                    it.copy(
                        isLoading = false,
                        starCast = credits.actors.map { it.toUi() },
                        characters = crew.filter { it.job == "Characters" }.take(3).map { it.name },
                        director = crew.filter {
                            it.job in (listOf(
                                "Director",
                                "Screenplay",
                                "Story"
                            ))
                        }.take(3).map { it.name },
                        writer = crew.filter { it.job == "Producer" }.take(3).map { it.name },
                        produce = crew.filter { it.job == "Writer" }.take(3).map { it.name }
                    )
                }
            },
            onError = { error ->
                updateState { it.copy(errorMessage = error.message.toString(), isLoading = false) }
            }
        )
    }

    private fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                updateState { it.copy(reviews = reviews.map { it.toUi() }) }
            },
            onError = { error ->
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
        launchWithResult(
            action = { preferences.isLoggedIn() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    updateState { it.copy(showRatingBottomSheet = true) }
                } else {
                    updateState { it.copy(showLoginBottomSheet = true) }
                }
            },
            onError = {
                updateState { it.copy(showLoginBottomSheet = true) }
            }
        )
    }

    override fun onDismissLoginBottomSheet() {
        updateState { it.copy(showLoginBottomSheet = false) }
    }

    override fun navigateToLogin() {
        sendEvent(SeriesDetailsScreenEffects.NavigateToLogin)
    }

    override fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    override fun onRatingSubmit(rating: Int, seriesId: Int) {
        launchAndForget(
            action = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
            onSuccess = { updateState { it.copy(starsRating = rating, showRatingBottomSheet = false) } },
            onError = { }, // TODO: Show Toast
        )
    }

    override fun onDeleteRatingSeries(seriesId: Int) {
        launchAndForget(
            action = { deleteRatingSeriesUseCase(seriesId) },
            onSuccess = { updateState { it.copy(starsRating = 0, showRatingBottomSheet = false) } },
            onError = { }, // TODO: Show Toast
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

    override fun onPlayButtonClicked() {
        sendEvent(SeriesDetailsScreenEffects.OpenTrailer(uiState.value.seriesDetail.trailerPath))
    }

    override fun onRetry() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = "") }
            loadSeriesDetails(seriesId)
            loadSeriesCredits(seriesId)
            getSeriesRecommendations(seriesId, page = 1)
            loadReviews(seriesId, page = 1)
            waitUntilAllDataIsReady()
            updateState { it.copy(isLoading = false) }
        }
    }
}