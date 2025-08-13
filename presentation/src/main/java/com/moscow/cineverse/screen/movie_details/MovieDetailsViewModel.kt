package com.moscow.cineverse.screen.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.base.handleException
import com.moscow.cineverse.mapper.toMovieItemUi
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
        getScreenData()
        observeBlur()
    }

    private fun getScreenData() {
        updateState {
            it.copy(
                isLoading = true,
                errorMessage = 0,
                shouldShowError = false
            )
        }
        viewModelScope.launch {
            try {
                val jobs = listOf(
                    async { getUserRating(movieId) },
                    async { getMovieDetails(movieId) },
                    async { getReviews(movieId) },
                    async { getCredits(movieId) },
                    async { getRecommendations(movieId) },
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

    private suspend fun getUserRating(seriesId: Int) {
        try {
            val res = getUserRatingForMovieUseCase.invoke(seriesId)
            updateState { it.copy(starsRating = res) }
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

    private suspend fun getMovieDetails(movieID: Int) {
        try {
            val res = getMovieDetailsUseCase.invoke(movieID)
//            onGetMovieDetailsSuccess(res)
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

//    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetail) {
//        updateState { it.copy(movieDetailsUiState = movieDetails.toUi()) }
//        launchWithResult(
//            action = { addRecentlyViewedMovieUseCase(movieDetails.toMovie()) },
//            onError = {},
//            onSuccess = {}
//        )
//    }

    private suspend fun getReviews(movieID: Int) {
        try {
            val res = getReviewsUseCase(movieID, 1, true)
            onGetReviewSuccess(res)
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

    private fun onGetReviewSuccess(reviews: List<Review>) {
//        updateState { state ->
//            state.copy(
//                reviewsFlow = reviews.take(3).map { it.toUi() }
//            )
//        }
    }

    private suspend fun getCredits(movieID: Int) {
        try {
            val res = getMovieCreditsUseCase(movieID)
            onGetCreditsSuccess(res)
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


    private fun onGetCreditsSuccess(creditsInfo: CreditsInfo) {
//        val crew = creditsInfo.behindTheScene.map { it.toUi() }
//        updateState { state ->
//            state.copy(
//                isLoading = false,
//                starCast = creditsInfo.actors.map { it.toUi() },
//                characters = crew.filter { it.job == "Characters" }.map { it.name },
//                director = crew.filter { it.job in (listOf("Director", "Screenplay", "Story")) }
//                    .map { it.name },
//                writer = crew.filter { it.job == "Producer" }.map { it.name },
//                produce = crew.filter { it.job == "Writer" }.map { it.name }
//            )
//        }
    }

    private suspend fun getRecommendations(movieID: Int) {
        try {
            val res = getMovieRecommendationsUseCase(movieID, 1)
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

    private fun onGetRecommendationsSuccess(recommendations: List<Movie>) {
        updateState { state ->
            state.copy(
                recommendations = recommendations.take(6).map { it.toMovieItemUi() }
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
            sendEvent(MovieDetailsScreenEffect.OpenTrailer(it.trailerUrl))
        }
    }

    override fun onRetry() {
        getScreenData()
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