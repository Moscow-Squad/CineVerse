package com.moscow.cineverse.screen.movie_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.mapper.toMediaItemUi
import com.moscow.cineverse.navigation.routes.MovieDetailsRoute
import com.moscow.cineverse.mapper.toUi
import com.moscow.domain.model.CreditsDetails
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Review
import com.moscow.domain.model.details.MovieDetail
import com.moscow.domain.usecase.movie.GetMovieCreditsUseCase
import com.moscow.domain.usecase.movie.GetMovieDetailsUseCase
import com.moscow.domain.usecase.movie.GetMovieRecommendationsUseCase
import com.moscow.domain.usecase.movie.RateMovieUseCase
import com.moscow.domain.usecase.review.GetReviewsUseCase
import kotlin.collections.take

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val rateMovieUseCase: RateMovieUseCase,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<MovieScreenState, MovieDetailsScreenEffect>(MovieScreenState()),
    MovieDetailsInteractionListener {

    private val movieId = saveStateHandle.get<Int>(MovieDetailsRoute.MOVIE_ID) ?: 0

    init {
        getMovieDetails(movieId)
        getReviews(movieId)
        getCredits(movieId)
        getRecommendations(movieId)
    }
    fun getMovieDetails(movieID: Int) {
        launchWithResult(
            action = { getMovieDetailsUseCase.invoke(movieID) },
            onSuccess = ::onGetMovieDetailsSuccess,
            onStart = ::onLoading,
            onError = ::getMovieDetailsFailed,
            onFinally = ::onFinally

        )

    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetail) {
        updateState { it.copy(movieDetailsUiState = movieDetails.toUi()) }
        Log.d("TAG", "onGetMovieDetailsSuccess: ${uiState}")
    }

    fun getReviews(movieID: Int) {
        launchWithResult(
            action = { getReviewsUseCase(movieID, 1, true) },
            onSuccess = ::onGetReviewSuccess,
            onStart = ::onLoading,
            onError = ::getReviewFailed,
            onFinally = ::onFinally

        )
    }

    private fun onGetReviewSuccess(reviews: List<Review>) {
        updateState { it.copy(reviewsFlow = reviews.take(3).map { it.toUi() }) }

    }
        fun getCredits(movieID: Int) {
            launchWithResult(
                action = { getMovieCreditsUseCase(movieID) },
                onSuccess = ::onGetCreditsSuccess,
                onError = ::getCreditsFailed,
            )
        }


    private fun onGetCreditsSuccess(creditsDetails: CreditsDetails) {
            val crew = creditsDetails.behindTheScene.map { it.toUi() }
            updateState {
                it.copy(
                    starCast = creditsDetails.actors.map { it.toUi() },
                    characters = crew.filter { it.job == "Characters" }.map { it.name },
                    director = crew.filter { it.job in (listOf("Director", "Screenplay", "Story")) }
                        .map { it.name },
                    writer = crew.filter { it.job == "Producer" }.map { it.name },
                    produce = crew.filter { it.job == "Writer" }.map { it.name }
                )
            }
        }

        fun getRecommendations(movieID: Int) {
            launchWithResult(
                action = { getMovieRecommendationsUseCase(movieID, 1) },
                onSuccess = ::onGetRecommendationsSuccess,
                onError = ::getRecommendationsFailed,
            )
        }

        private fun onGetRecommendationsSuccess(recommendations: List<Movie>) {
            updateState {
                it.copy(
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

        private fun getMovieDetailsFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
        }

        private fun getReviewFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
        }

        private fun getCreditsFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
        }

        private fun getRecommendationsFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString(), shouldShowError = true) }
        }

        override fun onBackPressed() {
            sendEvent(MovieDetailsScreenEffect.NavigateBack)
        }

    override fun onShowMoreCast() {
        //TODO:send event when user clicked on show more star cast
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

    override fun showRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = true) }
    }

    override fun onDismissOrCancelRatingBottomSheet() {
        updateState { it.copy(showRatingBottomSheet = false) }
    }

    override fun onRatingSubmit(rating: Int, movieId: Int) {
        launchWithResult(
            action = { rateMovieUseCase.invoke(rating.toFloat(), movieId) },
            onSuccess = {
                updateState { it.copy(
                    starsRating = rating,
                    showRatingBottomSheet = false,
                    isLoading = false
                ) }
            },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onError = {
                updateState {
                    it.copy(
                        starsRating = rating,
                        showRatingBottomSheet = false,
                        isLoading = false
                    )
                }
            }
        )
    }
}