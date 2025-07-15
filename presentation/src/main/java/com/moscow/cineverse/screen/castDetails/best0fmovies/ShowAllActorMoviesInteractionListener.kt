package com.moscow.cineverse.screen.castDetails.best0fmovies

import com.moscow.cineverse.designSystem.component.ViewMode

interface ShowAllActorMoviesInteractionListener {
    fun onRefresh()
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}