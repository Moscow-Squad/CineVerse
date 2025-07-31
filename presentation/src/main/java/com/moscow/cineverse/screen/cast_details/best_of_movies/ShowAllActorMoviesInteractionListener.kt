package com.moscow.cineverse.screen.cast_details.best_of_movies

import com.moscow.cineverse.utlis.ViewMode

interface ShowAllActorMoviesInteractionListener {
    fun onRefresh()
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}