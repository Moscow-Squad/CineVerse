package com.moscow.cineverse.screen.movie_details


import android.util.Log
import com.android.domain.model.CreditsDetails
import com.android.domain.model.Movie
import com.android.domain.model.Review
import com.android.domain.model.details.MovieDetail
import com.android.domain.usecase.GetCreditsUseCase
import com.android.domain.usecase.GetMovieDetailUseCase
import com.android.domain.usecase.GetRecommendationsUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.moscow.cineverse.base.BaseViewModel


class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase
) : BaseViewModel<MovieScreenState, MovieDetailsScreenEvents>(MovieScreenState()),
    MovieDetailsInteractionListener {

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
        updateState { it.copy(movieDetailsUi = movieDetails.toUi()) }
        Log.d("TAG", "onGetMovieDetailsSuccess: ${uiState}")
    }

    fun getReviews(movieID: Int) {
        launchWithResult(
            action = { getReviewsPageUseCase(movieID, 1, true) },
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
                action = { getCreditsUseCase(movieID) },
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
                action = { getRecommendationsUseCase(movieID, 1) },
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
            updateState { it.copy(errorMessage = error.message.toString()) }
        }

        private fun getReviewFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString()) }
        }

        private fun getCreditsFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString()) }
        }

        private fun getRecommendationsFailed(error: Throwable) {
            updateState { it.copy(errorMessage = error.message.toString()) }
        }


        override fun onBackPressed() {
            sendEvent(MovieDetailsScreenEvents.NavigateBack)
        }

    override fun onShowMoreCast() {
        //TODO:send event when user clicked on show more star cast
    }


    override fun onShowMoreRecommendations(movieId: Int, movieTitle: String) {
            sendEvent(MovieDetailsScreenEvents.NavigateToFullMovieList(movieId, movieTitle))
        }

        override fun onShowMoreReviews(movieId: Int) {
            sendEvent(MovieDetailsScreenEvents.NavigateToFullReviews(movieId))
        }

    override fun onActorClicked(actorId: Int) {
        sendEvent(MovieDetailsScreenEvents.NavigateCastDetails(actorId))
    }


}