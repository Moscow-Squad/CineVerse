package com.moscow.cineverse.screen.details.movie_details

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreRecommendations()
    fun onShowMoreReviews(movieId: Int)
    fun onAddToCollection(mediaItemId: Int)
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onDismissLoginBottomSheet()
    fun navigateToLogin()
    fun onRatingSubmit(rating: Int, movieId: Int)
    fun onDeleteRatingMovie(movieId: Int)
    fun onActorClicked(actorId: Int)
    fun onMovieClicked(movieId: Int)
    fun onPlayButtonClicked()
    fun onRetry()
}