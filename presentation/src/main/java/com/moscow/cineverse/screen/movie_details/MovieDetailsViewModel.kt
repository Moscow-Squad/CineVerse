package com.moscow.cineverse.screen.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cinverse.presentation.R
import com.moscow.domain.mapper.toMovie
import com.moscow.domain.model.CreditsInfo
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.repository.auth.UserRepository
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.movie.DeleteRatingMovieUseCase
import com.moscow.domain.usecase.movie.GetMovieCreditsUseCase
import com.moscow.domain.usecase.movie.GetMovieDetailsUseCase
import com.moscow.domain.usecase.movie.GetMovieRecommendationsUseCase
import com.moscow.domain.usecase.movie.GetUserRatingForMovieUseCase
import com.moscow.domain.usecase.movie.RateMovieUseCase
import com.moscow.domain.usecase.recently_viewed.AddRecentlyViewedMovieUseCase
import com.moscow.domain.usecase.review.GetReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val rateMovieUseCase: RateMovieUseCase,
    private val blurProvider: BlurProvider,
    private val deleteRatingMovieUseCase: DeleteRatingMovieUseCase,
    private val getUserRatingForMovieUseCase: GetUserRatingForMovieUseCase,
    private val addRecentlyViewedMovieUseCase: AddRecentlyViewedMovieUseCase,
    private val preferences: UserRepository,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<MovieScreenState, MovieDetailsScreenEffect>(MovieScreenState()),
    MovieDetailsInteractionListener {

    private val movieId = saveStateHandle.get<Int>(MovieDetailsRoute.MOVIE_ID) ?: 0


    init {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            getUserRating(movieId)
            getMovieDetails(movieId)
            getReviews(movieId)
            getCredits(movieId)
            getRecommendations(movieId)
            waitUntilAllDataIsReady()
            updateState { it.copy(isLoading = false) }
        }
        observeBlur()
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
            action = { getUserRatingForMovieUseCase.invoke(seriesId) },
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
        while (uiState.value.movieDetailsUiState == null) {
            wait++
            if (wait == 25) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = R.string.too_much_time,
                        shouldShowError = true
                    )
                }
                return
            }
            delay(100)
        }
    }

    fun getMovieDetails(movieID: Int) {
        updateState { it.copy(isLoading = true) }
        launchWithResult(
            action = { getMovieDetailsUseCase.invoke(movieID) },
            onSuccess = ::onGetMovieDetailsSuccess,
            onStart = ::onLoading,
            onError = ::getMovieDetailsFailed,
            onFinally = ::onFinally
        )
    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetail) {
        updateState { it.copy(isLoading = false, movieDetailsUiState = movieDetails.toUi()) }
        launchWithResult(
            action = { addRecentlyViewedMovieUseCase(movieDetails.toMovie()) },
            onError = {},
            onSuccess = {}
        )
    }

    fun getReviews(movieID: Int) {
        updateState { it.copy(isLoading = true) }
        launchWithResult(
            action = { getReviewsUseCase(movieID, 1, true) },
            onSuccess = ::onGetReviewSuccess,
            onStart = ::onLoading,
            onError = ::getReviewFailed,
            onFinally = ::onFinally

        )
    }

    private fun onGetReviewSuccess(reviews: List<Review>) {
        updateState { state ->
            state.copy(
                isLoading = false,
                reviewsFlow = reviews.take(3).map { it.toUi() })
        }

    }

    fun getCredits(movieID: Int) {
        updateState { it.copy(isLoading = true) }
        launchWithResult(
            action = { getMovieCreditsUseCase(movieID) },
            onSuccess = ::onGetCreditsSuccess,
            onError = ::getCreditsFailed,
        )
    }


    private fun onGetCreditsSuccess(creditsInfo: CreditsInfo) {
        val crew = creditsInfo.behindTheScene.map { it.toUi() }
        updateState { state ->
            state.copy(
                isLoading = false,
                starCast = creditsInfo.actors.map { it.toUi() },
                characters = crew.filter { it.job == "Characters" }.map { it.name },
                director = crew.filter { it.job in (listOf("Director", "Screenplay", "Story")) }
                    .map { it.name },
                writer = crew.filter { it.job == "Producer" }.map { it.name },
                produce = crew.filter { it.job == "Writer" }.map { it.name }
            )
        }
    }

    fun getRecommendations(movieID: Int) {
        updateState { it.copy(isLoading = true) }
        launchWithResult(
            action = { getMovieRecommendationsUseCase(movieID, 1) },
            onSuccess = ::onGetRecommendationsSuccess,
            onError = ::getRecommendationsFailed,
        )
    }

    private fun onGetRecommendationsSuccess(recommendations: List<Movie>) {
        updateState { state ->
            state.copy(
                isLoading = false,
                recommendations = recommendations.take(6).map { it.toMediaItemUi() }
            )
        }
    }


    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }

    private fun getMovieDetailsFailed(msg: Int) {
        updateState {
            it.copy(
                errorMessage = msg,
                shouldShowError = true,
                isLoading = false
            )
        }
    }

    private fun getReviewFailed(msg: Int) {
        updateState {
            it.copy(
                errorMessage = msg,
                shouldShowError = true,
                isLoading = false
            )
        }
    }

    private fun getCreditsFailed(msg: Int) {
        updateState {
            it.copy(
                errorMessage = msg,
                shouldShowError = true,
                isLoading = false
            )
        }
    }

    private fun getRecommendationsFailed(msg: Int) {
        updateState {
            it.copy(
                errorMessage = msg,
                shouldShowError = true,
                isLoading = false
            )
        }
    }

    override fun onBackPressed() {
        sendEvent(MovieDetailsScreenEffect.NavigateBack)
    }

    override fun onShowMoreRecommendations(movieId: Int, movieTitle: String) {
        sendEvent(MovieDetailsScreenEffect.NavigateToFullMovieList(movieId, movieTitle))
    }

    override fun onShowMoreReviews(movieId: Int) {
        sendEvent(MovieDetailsScreenEffect.NavigateToFullReviews(movieId))
    }

    override fun onAddToCollection(mediaItemId: Int) {
        sendEvent(MovieDetailsScreenEffect.AddToCollection(mediaItemId))

    }

    override fun onActorClicked(actorId: Int) {
        sendEvent(MovieDetailsScreenEffect.NavigateCastDetails(actorId))
    }

    override fun onMovieClicked(movieId: Int) {
        sendEvent(MovieDetailsScreenEffect.NavigateMovieDetails(movieId))
    }

    override fun onPlayButtonClicked() {
        uiState.value.movieDetailsUiState?.let {
            sendEvent(MovieDetailsScreenEffect.OpenTrailer(it.trailerPath))
        }
    }

    override fun onRetry() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            getMovieDetails(movieId)
            getReviews(movieId)
            getCredits(movieId)
            getRecommendations(movieId)
            waitUntilAllDataIsReady()
            updateState { it.copy(isLoading = false) }
        }
    }

    override fun showRatingBottomSheet() {
        launchWithResult(
            action = { preferences.isLoggedIn() },
            onSuccess = { isLoggedIn ->
                if (isLoggedIn) {
                    updateState { it.copy(
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
        sendEvent(MovieDetailsScreenEffect.NavigateToLogin)
    }

    override fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    override fun onRatingSubmit(rating: Int, movieId: Int) {
        launchWithResult(
            action = { rateMovieUseCase.invoke(rating.toFloat(), movieId) },
            onSuccess = {
                updateState {
                    it.copy(
                        starsRating = rating,
                        showRatingBottomSheet = false,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState { it.copy(showRatingBottomSheet = false) }
                sendEvent(
                    MovieDetailsScreenEffect.ShowError(message = "There is a problem receiving the request from the server. Please come back later.")
                    // TODO : Handle Arabic Message
                )
            }
        )
    }

    override fun onDeleteRatingMovie(movieId: Int) {
        launchWithResult(
            action = { deleteRatingMovieUseCase.invoke(movieId) },
            onSuccess = {
                updateState {
                    it.copy(
                        starsRating = 0,
                        showRatingBottomSheet = false,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState { it.copy(showRatingBottomSheet = false) }
                sendEvent(
                    MovieDetailsScreenEffect.ShowError(message = "There is a problem receiving the request from the server. Please come back later.")
                    // TODO : Handle Arabic Message
                )
            }
        )
    }
}