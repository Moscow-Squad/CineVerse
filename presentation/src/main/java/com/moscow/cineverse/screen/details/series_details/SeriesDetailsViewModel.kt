package com.moscow.cineverse.screen.details.series_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.base.handleException
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Series
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.usecase.rating.DeleteRatingSeriesUseCase
import com.moscow.domain.usecase.rating.GetUserRatingForSeriesUseCase
import com.moscow.domain.usecase.rating.RateSeriesUseCase
import com.moscow.domain.usecase.recently_viewed.AddRecentlyViewedSeriesUseCase
import com.moscow.domain.usecase.review.GetReviewsUseCase
import com.moscow.domain.usecase.series.GetSeriesCreditsDetailsUseCase
import com.moscow.domain.usecase.series.GetSeriesDetailUseCase
import com.moscow.domain.usecase.series.GetSeriesRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val getSeriesDetailUseCase: GetSeriesDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsUseCase,
    private val rateSeriesUseCase: RateSeriesUseCase,
    private val getSeriesCreditsDetailsUseCase: GetSeriesCreditsDetailsUseCase,
    private val getSeriesRecommendationsUseCase: GetSeriesRecommendationsUseCase,
    private val blurProvider: BlurProvider,
    private val addRecentlyViewedSeriesUseCase: AddRecentlyViewedSeriesUseCase,
    private val deleteRatingSeriesUseCase: DeleteRatingSeriesUseCase,
    private val getUserRatingForSeriesUseCase: GetUserRatingForSeriesUseCase,
    private val preferences: UserRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsScreenEffects>(SeriesDetailsScreenState()),
    SeriesDetailsInteractionListener {

    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    init {
        getScreenData()
        observeBlur()
    }

    private fun getScreenData() {
        updateState {
            it.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = 0
            )
        }
        viewModelScope.launch {
            try {
                val jobs = listOf(
                    async { getUserRating(seriesId) },
                    async { loadSeriesDetails() },
                    async { loadReviews(seriesId, page = 1) },
                    async { loadSeriesCredits(seriesId) },
                    async { getSeriesRecommendations(seriesId, page = 1) },
                )
                jobs.awaitAll()
                updateState { it.copy(isLoading = false) }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        errorMessage = e.handleException(),
                        shouldShowError = true,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
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

    private suspend fun loadSeriesDetails() {
        try {
            val detail = getSeriesDetailUseCase(seriesId)
            updateState { it.copy(seriesDetail = detail.toUi()) }
            launchWithResult(
                action = { addRecentlyViewedSeriesUseCase(detail) },
                onSuccess = {},
                onError = {}
            )
        } catch (e: Exception) {
            updateState {
                it.copy(
                    errorMessage = e.handleException(),
                    shouldShowError = true,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun loadSeriesCredits(seriesId: Int) {
        try {
            val credits = getSeriesCreditsDetailsUseCase(seriesId)
            onSuccessLoadSeriesCredits(credits)
        } catch (e: Exception) {
            updateState {
                it.copy(
                    errorMessage = e.handleException(),
                    shouldShowError = true,
                    isLoading = false
                )
            }
        }
    }

    private fun onSuccessLoadSeriesCredits(credits: CreditsInfo) {
        updateState {
            it.copy(
                isLoading = false,
                starCast = credits.cast.map { actor -> actor.toUi() },
                characters = credits.crew.filter { it.job == "Characters" }.take(3).map { it.name },
                director = credits.crew.filter {
                    it.job in (listOf(
                        "Director",
                        "Screenplay",
                        "Story"
                    ))
                }.take(3).map { it.name },
                writer = credits.crew.filter { it.job == "Producer" }.take(3).map { it.name },
                produce = credits.crew.filter { it.job == "Writer" }.take(3).map { it.name }
            )
        }
    }

    private fun loadReviews(seriesId: Int, page: Int) {
        updateState { it.copy(isLoading = true, errorMessage = 0, shouldShowError = false) }
        launchWithResult(
            action = { getReviewsPageUseCase(seriesId, page, isMovie = false) },
            onSuccess = { reviews ->
                Log.e("jkvjvnj", "onSuccessLoadReviews: $reviews")

                updateState { it.copy(reviews = reviews.map { it.toUi() }) }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error,
                        isLoading = false,
                        shouldShowError = true
                    )
                }
            }
        )
    }

    private suspend fun getSeriesRecommendations(seriesId: Int, page: Int) {
        try {
            val res = getSeriesRecommendationsUseCase(seriesId, page)
            onGetRecommendationsSuccess(res)
        } catch (e: Exception) {
            updateState {
                it.copy(
                    errorMessage = e.handleException(),
                    shouldShowError = true,
                    isLoading = false
                )
            }
        }

    }

    private fun onGetRecommendationsSuccess(recommendations: List<Series>) {
        updateState { it.copy(recommendation = recommendations.map { it.toMediaItemUi() }.take(6)) }
    }

    override fun showRatingBottomSheet() {
        launchWithResult(
            action = { preferences.isLoggedIn() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    updateState {
                        it.copy(
                            starsRating = uiState.value.starsRating,
                            showRatingBottomSheet = true
                        )
                    }
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
        updateState { it.copy(showLoginBottomSheet = false) }
        updateState { it.copy(showRatingBottomSheet = false) }
        sendEvent(SeriesDetailsScreenEffects.NavigateToLogin)
    }

    override fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    override fun onRatingSubmit(rating: Int) {
        launchAndForget(
            action = { rateSeriesUseCase.rateSeriesUse(rating.toFloat(), seriesId) },
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

    override fun onDeleteRatingSeries() {
        launchAndForget(
            action = { deleteRatingSeriesUseCase(seriesId) },
            onSuccess = { updateState { it.copy(starsRating = 0, showRatingBottomSheet = false) } },
            onError = { }, // TODO: Show Toast
        )
    }

    override fun onShowMoreRecommendationsClicked() {
        sendEvent(
            SeriesDetailsScreenEffects.NavigateToRecommendationSeries(
                seriesId,
                uiState.value.seriesDetail.title
            )
        )
    }

    override fun onShowMoreReviewsClicked() {
        sendEvent(SeriesDetailsScreenEffects.NavigateToReviewsScreen(seriesId))
    }

    override fun onShowMoreSeasonsClicked() {
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
        getScreenData()
    }
}