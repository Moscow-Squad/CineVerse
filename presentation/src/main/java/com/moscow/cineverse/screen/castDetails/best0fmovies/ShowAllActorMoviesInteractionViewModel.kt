package com.moscow.cineverse.screen.castDetails.best0fmovies

import com.android.domain.model.Movie
import com.android.domain.usecase.genre.GenreUseCase
import com.android.domain.usecase.actor.GetActorBestMoviesUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ShowAllActorMoviesInteractionViewModel(
    private val getActorBestMoviesUseCase: GetActorBestMoviesUseCase,
    actorId: Int,
    private val genreUseCase: GenreUseCase
) : BaseViewModel<ShowAllActorMoviesState, ShowAllActorMoviesEvents>(ShowAllActorMoviesState()),
    ShowAllActorMoviesInteractionListener {
    init {
        updateState { it.copy(actorId = actorId) }
        getActorMovies()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getActorMovies() {
        launchWithResult(
            action = {
                val genres = genreUseCase.getMoviesGenres()
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
                getActorBestMoviesUseCase.invoke(uiState.value.actorId)
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
        sendEvent(ShowAllActorMoviesEvents.NavigateMovieDetails(movieId))
    }

    override fun backButtonClick() {

    }
}