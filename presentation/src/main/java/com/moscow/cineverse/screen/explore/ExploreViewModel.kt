package com.moscow.cineverse.screen.explore

import androidx.lifecycle.viewModelScope
import com.android.domain.model.Genre
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSeriesUseCase: GetSeriesUseCase
) : BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {



    init {
        viewModelScope.launch {
            uiState.value.fromScreenState(uiState.value.selectedTab)
        }

        loadData()
    }


    override fun onGenreSelected(genre: Genre) {
        updateState { it.copy(selectedGenre = genre) }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState {it.copy(viewMode = viewMode) }
    }

    override fun onMovieClick(movieId: Int) {
        sendEvent(ExploreScreenEvents.MovieClicked(movieId))
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        updateState { it.copy(selectedTab = tab) }
        loadData()
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        launchWithResult(
            action = {
                val movies = getMoviesUseCase()
                val series = getSeriesUseCase()
                val genres = generateSampleGenres()

                Triple(movies, series, genres)
            },
            onSuccess = { (movies, series, genres) ->
                updateState {
                    it.copy(
                        movies = movies.map{ movie -> movie.toUi()},
                        series = series.map{ series -> series.toUi()},
                        genres = genres,
                        selectedGenre = genres.firstOrNull(),
                        isLoading = false,
                        error = null
                    )
                }
            },
            onError = { exception ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "An error occurred"
                    )
                }
            },
            onStart = {
                updateState { it.copy(isLoading = true, error = null) }
            }
        )
    }

    private fun generateSampleGenres(): List<Genre> {
        return listOf(
            Genre(1, "Action"),
            Genre(2, "Comedy"),
            Genre(3, "Drama"),
            Genre(4, "Horror"),
            Genre(5, "Sci-Fi"),
            Genre(6, "Romance"),
            Genre(7, "Thriller"),
            Genre(8, "Adventure")
        )
    }
}