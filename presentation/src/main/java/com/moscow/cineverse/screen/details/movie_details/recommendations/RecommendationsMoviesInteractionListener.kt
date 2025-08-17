package com.moscow.cineverse.screen.details.movie_details.recommendations

import com.moscow.cineverse.utlis.ViewMode

interface RecommendationsMoviesInteractionListener {
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
    fun onRetry()
}