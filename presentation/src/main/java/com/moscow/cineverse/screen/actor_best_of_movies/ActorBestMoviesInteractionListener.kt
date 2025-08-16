package com.moscow.cineverse.screen.actor_best_of_movies

import com.moscow.cineverse.utlis.ViewMode

interface ActorBestMoviesInteractionListener {
    fun onRefresh()
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}