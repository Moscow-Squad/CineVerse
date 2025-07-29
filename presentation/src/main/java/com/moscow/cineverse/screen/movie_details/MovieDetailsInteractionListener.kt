package com.moscow.cineverse.screen.movie_details

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreRecommendations( movieId:Int,  movieTitle:String)
    fun onShowMoreReviews(movieId:Int)
    fun onAddToCollection(mediaItemId: Int)
    fun showRatingBottomSheet()
    fun onDismissOrCancelRatingBottomSheet()
    fun onRatingSubmit(rating: Int, movieId: Int)
    fun onActorClicked(actorId: Int)
    fun onMovieClicked(movieId: Int)
    fun onRetry()
}