package com.moscow.cineverse.screen.castDetails.best0fmovies

import com.android.domain.model.Movie
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.actordetails.GetActorBestOfMovies
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.screen.mapper.toUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat

class ShowAllActorMoviesInteractionViewModel(
    private val getActorBestOfMovies: GetActorBestOfMovies,
    actorId: Int,
    private val genreUseCase: GenreUseCase
) : BaseViewModel<ShowAllActorMoviesState, ShowAllActorMoviesEffect>(ShowAllActorMoviesState()),
    ShowAllActorMoviesInteractionListener {
    init {
        updateState { it.copy(actorId = actorId) }
        getActorMovies()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getActorMovies() {
        launchWithFlow(
            flowAction = {
                genreUseCase.getMoviesGenres().flatMapConcat { genres ->
                    updateState { it.copy(moviesGenres = genres.map { it.toUi() }) }
                    getActorBestOfMovies.getActorBestOfMovies(uiState.value.actorId)
                }
            },
            onSuccess = ::onGetMovieSuccess,
            onError = ::onGetMovieFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetMovieSuccess(movies: List<Movie>) {
        updateState { showAllActorMoviesState ->
            showAllActorMoviesState.copy(movies = movies.toUi(uiState.value.moviesGenres))
        }
    }

    private fun onGetMovieFailed(throwable: Throwable) {
        updateState { it.copy(error = throwable.message) }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }

    override fun onRefresh() {
        getActorMovies()
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {
            it.copy(viewMode = viewMode)
        }
    }

    override fun onMovieClick(movieId: Int) {
        sendEvent(ShowAllActorMoviesEffect.NavigateMovieDetails(movieId))
    }

    override fun backButtonClick() {

    }
}