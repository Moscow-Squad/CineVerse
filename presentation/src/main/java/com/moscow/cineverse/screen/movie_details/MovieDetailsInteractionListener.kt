package com.moscow.cineverse.screen.movie_details

import com.android.domain.model.Movie

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreCast()
    fun onShowMoreRecommendations( movieId:Int,  movieTitle:String)
    fun onShowMoreReviews(movieId:Int)
    fun onActorClicked(actorId: Int)
}