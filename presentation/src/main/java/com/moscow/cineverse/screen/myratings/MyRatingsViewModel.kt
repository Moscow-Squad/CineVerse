package com.moscow.cineverse.screen.myratings

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.ExploreTabsPages
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.movie.GetRatedMoviesUseCase
import com.moscow.domain.usecase.series.GetRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MyRatingsViewModel @Inject constructor(
    private val ratedMoviesUseCase: GetRatedMoviesUseCase,
    private val ratedSeriesUseCase: GetRatedSeriesUseCase,
    private val genreUseCase: GenreUseCase,
) : BaseViewModel<MyRatingsUiState, MyRatingsEffect>(MyRatingsUiState()),
    MyRatingsInteractionListener {

    lateinit var contentList: Flow<PagingData<RatedMediaItem>>

    private lateinit var _ratedMovies: Flow<PagingData<RatedMediaItem>>
    private lateinit var _ratedSeries: Flow<PagingData<RatedMediaItem>>

    init {
        getRatedMovies()
        getRatedSeries()
        getMoviesGenres()
        getSeriesGenres()
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        if (uiState.value.selectedTab == tab) return

        updateState { it.copy(selectedTab = tab) }
        updateContentList()
    }

    override fun onMediaItemClicked(mediaItemUiState: MediaItemUiState) {
        if (mediaItemUiState.mediaType == MediaType.Movie) {
            sendEvent(MyRatingsEffect.MovieClicked(mediaItemUiState.id))
        } else {
            sendEvent(MyRatingsEffect.SeriesClicked(mediaItemUiState.id))
        }
    }

    override fun onNavigateBack() {
        sendEvent(MyRatingsEffect.NavigateBack)
    }

    override fun onRefresh() {
        updateState {
            it.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = ""
            )
        }

        getRatedMovies()
        getRatedSeries()
    }

    private fun getRatedMovies() {
        _ratedMovies = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    ratedMoviesUseCase.invoke(page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { movie -> movie.toUi(uiState.value.movieGenres) }
        }.cachedIn(viewModelScope)

        updateContentList()
    }

    private fun getRatedSeries() {
        _ratedSeries = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    ratedSeriesUseCase.invoke(page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { series -> series.toUi(uiState.value.seriesGenres) }
        }.cachedIn(viewModelScope)

        updateContentList()
    }

    private fun updateContentList() {
        contentList = when (uiState.value.selectedTab) {
            ExploreTabsPages.MOVIES -> _ratedMovies
            ExploreTabsPages.SERIES -> _ratedSeries
            ExploreTabsPages.ACTORS -> throw IllegalStateException("Actors tab should not be available in ratings")
        }
    }

    private fun getMoviesGenres() {
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        movieGenres = genres.map { genre -> genre.toUi() },
                        isLoading = false
                    )
                }
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }

    private fun getSeriesGenres() {
        launchWithResult(
            action = { genreUseCase.getSeriesGenres() },
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        seriesGenres = genres.map { genre -> genre.toUi() },
                        isLoading = false
                    )
                }
            },
            onError = { e ->
                updateState {
                    it.copy(
                        isLoading = false,
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }
}