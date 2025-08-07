package com.moscow.cineverse.screen.movie_details.recommendations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.cineverse.navigation.routes.RecommendationsRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.domain.model.Movie
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.movie.GetMovieRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationsMoviesViewModel @Inject constructor(
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
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
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    fun getRecommendations(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getMovieRecommendationsUseCase(movieId, page)
                }
            }
        ).flow.cachedIn(viewModelScope)
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