package com.moscow.cineverse.screen.cast_details

import com.moscow.cineverse.designSystem.component.ViewMode

interface ShowAllActorMoviesInteractionListener {
    fun onRefresh()
    fun onViewModeChanged(viewMode: ViewMode)
    fun onMovieClick(movieId: Int)
    fun backButtonClick()
}