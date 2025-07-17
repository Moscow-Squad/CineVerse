package com.moscow.cineverse.screen.movie_details

import com.android.domain.model.Movie

interface MovieDetailsInteractionListener {
    fun onBackPressed()
    fun onShowMoreCast()
    fun onShowMoreCrew()
    fun onShowMoreRecommendations()
    fun onShowMoreReviews()
}