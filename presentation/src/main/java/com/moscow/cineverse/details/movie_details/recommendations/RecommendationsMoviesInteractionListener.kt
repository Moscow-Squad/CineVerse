package com.moscow.cineverse.details.movie_details.recommendations

import com.moscow.cineverse.utlis.ViewMode

interface RecommendationsMoviesInteractionListener {
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}