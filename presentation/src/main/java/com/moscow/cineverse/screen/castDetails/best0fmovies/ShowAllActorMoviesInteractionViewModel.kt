package com.moscow.cineverse.screen.castDetails.best0fmovies

import androidx.lifecycle.SavedStateHandle
import com.android.domain.model.Movie
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.actordetails.GetActorBestOfMovies
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat

class ShowAllActorMoviesInteractionViewModel (
   private val getActorBestOfMovies: GetActorBestOfMovies,
    savedStateHandle: SavedStateHandle,
    private val genreUseCase: GenreUseCase
): BaseViewModel<ShowAllActorMoviesState, ShowAllActorMoviesEvents>(ShowAllActorMoviesState()),
ShowAllActorMoviesInteractionListener {
    //todo enhance when make navigation
    private val actorId = savedStateHandle.get<Int>("actorId")?: 1
    init {
        getActorMovies()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getActorMovies(){
        launchWithFlow(
            flowAction = {
                genreUseCase.getMoviesGenres().flatMapConcat { genres ->
                    updateState { it.copy(moviesGenres = genres.map { it.toUi() }) }
                    getActorBestOfMovies.getActorBestOfMovies(actorId)
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

    }

    override fun backButtonClick() {

    }


}