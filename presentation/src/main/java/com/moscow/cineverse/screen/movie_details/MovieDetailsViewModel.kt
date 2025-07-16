package com.moscow.cineverse.screen.movie_details


import android.util.Log
import com.android.domain.model.MovieDetail
import com.android.domain.model.Review
import com.android.domain.usecase.GetMovieDetailUseCase
import com.android.domain.usecase.GetReviewsPageUseCase
import com.moscow.cineverse.base.BaseViewModel


class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailUseCase,
    private val getReviewsPageUseCase: GetReviewsPageUseCase,
) : BaseViewModel<MovieScreenState, MovieDetailsScreenEvents>(MovieScreenState()) {

    init{
        getMovieDetails(24428)

    }
    private fun getMovieDetails(movieID:Int) {
        launchWithResult(
            action = {getMovieDetailsUseCase.invoke(movieID)} ,
            onSuccess = ::onGetMovieDetailsSuccess,
            onStart = ::onLoading,
            onError =::getMovieDetailsFailed,
            onFinally = ::onFinally

        )

    }

    private fun onGetMovieDetailsSuccess(movieDetails: MovieDetail){
        launchWithResult(
            action = {getReviewsPageUseCase(24428, 1, true)} ,
            onSuccess = ::onGetReviewSuccess,
            onStart = ::onLoading,
            onError =::getReviewFailed,
            onFinally = ::onFinally

        )

        updateState { it.copy(movieDetailsUi = movieDetails.toUi())}
        Log.d("TAG", "onGetMovieDetailsSuccess: ${uiState}")
    }
    private fun onGetReviewSuccess(reviews:List<Review>){
        updateState { it.copy(reviewsFlow = reviews.map {it.toUi()}) }
    }
    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
    private fun getMovieDetailsFailed(error: Throwable){
        updateState { it.copy(errorMessage = error.message.toString()) }
    }
    private fun getReviewFailed(error: Throwable){

    }



}