package com.moscow.cineverse.screen.movie_details

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreCast()
    fun onShowMoreRecommendations( movieId:Int,  movieTitle:String)
    fun onShowMoreReviews(movieId:Int)
    fun onAddToCollection(mediaItemId: Int)
}