package com.moscow.cineverse.screen.details.movie_details.recommendations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.mapper.toUi
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.movie.GetMovieRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationsMoviesViewModel @Inject constructor(
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val genreUseCase: GenreUseCase,
    private val blurProvider: BlurProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RecommendationsMoviesState,
        RecommendationMoviesEffect>(RecommendationsMoviesState()),
    RecommendationsMoviesInteractionListener {

    val movieId: Int = savedStateHandle.get<Int>(RecommendationsRoute.MOVIE_ID) ?: 0
    private val movieTitle: String =
        savedStateHandle.get<String>(RecommendationsRoute.MOVIE_TITLE) ?: ""

    init {
        updateState { it.copy(movieId = movieId, movieTitle = movieTitle) }
        getMovieGenre()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun getMovieGenre() {
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        moviesGenres = genres.map { genre -> genre.toUi() },
                        isLoading = false
                    )
                }
                getRecommendations()
            },
            onError = { e ->
                updateState {
                    it.copy(
                        error = e,
                        isLoading = false,
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }

    private fun getRecommendations(){
        updateState { it.copy(
            recommendation = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    BasePagingSource { page ->
                        getMovieRecommendationsUseCase(movieId, page).toUi(uiState.value.moviesGenres)
                    }
                }
            ).flow.cachedIn(viewModelScope)
        ) }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {
            it.copy(viewMode = viewMode)
        }
    }

    override fun onMovieClick(movieId: Int) {
        sendEvent(RecommendationMoviesEffect.MovieClicked(movieId))
    }

    override fun backButtonClick() {
        sendEvent(RecommendationMoviesEffect.NavigateBack)
    }
}
