package com.moscow.cineverse.screen.cast_details

import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode

class ShowAllActorMoviesInteractionViewModel : BaseViewModel<ShowAllActorMoviesState, ShowAllActorMoviesEvents>(ShowAllActorMoviesState()),
ShowAllActorMoviesInteractionListener {
    override fun onRefresh() {

    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {
            it.copy(viewMode = viewMode)
        }

    }

    override fun onMovieClick(movieId: Int) {

    }

    override fun backButtonClick() {

    }


}