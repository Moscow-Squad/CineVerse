package com.moscow.cineverse.screen.movie_details.recommendations

import com.moscow.cineverse.designSystem.component.ViewMode

interface RecommendationsMoviesInteractionListener {

    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}