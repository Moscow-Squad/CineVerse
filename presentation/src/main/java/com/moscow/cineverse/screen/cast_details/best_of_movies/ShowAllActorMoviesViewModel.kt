package com.moscow.cineverse.screen.cast_details.best_of_movies

import androidx.lifecycle.SavedStateHandle
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.navigation.routes.CastBestOfMovieRoute
import com.moscow.cineverse.navigation.routes.SeriesDetailsRoute
import com.moscow.domain.model.Movie
import com.moscow.domain.usecase.actor.GetActorBestMoviesUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class ShowAllActorMoviesViewModel @Inject constructor(
    private val getActorBestMoviesUseCase: GetActorBestMoviesUseCase,
    private val genreUseCase: GenreUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ShowAllActorMoviesState, ShowAllActorMoviesEffect>(ShowAllActorMoviesState()),
    ShowAllActorMoviesInteractionListener {
    val seriesId = savedStateHandle.get<Int>(SeriesDetailsRoute.SERIES_ID) ?: 0

    private val actorId = savedStateHandle.get<Int>(CastBestOfMovieRoute.CAST_ID) ?: 0
    private val actorName = savedStateHandle.get<String>(CastBestOfMovieRoute.CAST_NAME) ?: ""

    init {
        updateState { it.copy(actorId = actorId, actorName = actorName) }
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
        sendEvent(ShowAllActorMoviesEffect.NavigateMovieDetails(movieId))
    }

    override fun backButtonClick() {

    }
}