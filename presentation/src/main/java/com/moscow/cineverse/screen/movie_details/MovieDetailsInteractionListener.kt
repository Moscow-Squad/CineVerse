package com.moscow.cineverse.screen.movie_details

import com.moscow.cineverse.common_ui_state.MediaItemUiState

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreCast()
    fun onShowMoreRecommendations( movieId:Int,  movieTitle:String)
    fun onShowMoreReviews(movieId:Int)
    fun onAddToCollection(mediaItemId: Int)
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onRatingSubmit(rating: Int, movieId: Int)
    fun onActorClicked(actorId: Int)
    fun onMovieItemClicked(movieId: Int)
}