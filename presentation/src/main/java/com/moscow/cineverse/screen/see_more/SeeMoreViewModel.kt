package com.moscow.cineverse.screen.see_more

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.home.HomeFeaturedItems
import com.moscow.domain.model.MediaType
import com.moscow.domain.model.Movie
import com.moscow.domain.model.Series
import com.moscow.domain.repository.MovieRepository
import com.moscow.domain.repository.SeriesRepository
import com.moscow.domain.usecase.movie.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SeeMoreViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val seriesRepository: SeriesRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<SeeMoreUiState, SeeMoreEvent>(
    SeeMoreUiState(
        title = savedStateHandle.get<String>("category") ?: ""
    )
), SeeMoreInteractionListener {

    private val _pagingDataFlow = MutableStateFlow<Flow<PagingData<MediaItemUiState>>>(emptyFlow())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        updateState { it.copy(isLoading = true) }
        val category = savedStateHandle.get<String>("category") ?: return
        val pageSize = 20

        viewModelScope.launch {
            try {
                val pagingFlow = when (category) {
                    HomeFeaturedItems.RECENTLY_RELEASED.name -> {
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> getPopularMoviesUseCase(page) },
                            mediaType = MediaType.Movie
                        )
                    }
                    HomeFeaturedItems.UPCOMING_MOVIES.name -> {
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> moviesRepository.getPopularMovies(page) }, // Replace with getUpcomingMovies when available
                            mediaType = MediaType.Movie
                        )
                    }
                    HomeFeaturedItems.MATCHES_YOUR_VIBE.name -> {
                        // Using genre ID 28 for Action, same as in HomeViewModel
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> moviesRepository.getMoviesByGenreId(28, page) },
                            mediaType = MediaType.Movie
                        )
                    }
                    HomeFeaturedItems.TOP_RATED_TV_SHOWS.name -> {
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> seriesRepository.getPopularSeries(page) },
                            mediaType = MediaType.Tv
                        )
                    }
                    HomeFeaturedItems.YOU_RECENTLY_VIEWED.name -> {
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> moviesRepository.getPopularMovies(page) }, // Replace with getRecentlyViewed when available
                            mediaType = MediaType.Movie
                        )
                    }
                    else -> emptyFlow()
                }

                _pagingDataFlow.value = pagingFlow
                updateState { it.copy(isLoading = false) }
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, shouldShowError = true) }
            }
        }
    }

    private fun <T : Any> createPagingFlow(
        pageSize: Int,
        fetchData: suspend (Int) -> List<T>,
        mediaType: MediaType
    ): Flow<PagingData<MediaItemUiState>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BasePagingSource<T>(fetchData) }
        ).flow
            .map { pagingData ->
                pagingData.map { item ->
                    when (item) {
                        is Movie -> movieToUiState(item)
                        is Series -> seriesToUiState(item)
                        else -> throw IllegalArgumentException("Unsupported type: ${item::class.java}")
                    }
                }
            }
            .cachedIn(viewModelScope)
    }



    override fun onRefresh() {
        loadContent()
    }

    override fun onMediaItemClicked(id: Int) {
        val category = savedStateHandle.get<String>("category") ?: return
        val event = when {
            category == HomeFeaturedItems.TOP_RATED_TV_SHOWS.name -> SeeMoreEvent.SeriesClicked(id)
            else -> SeeMoreEvent.MovieClicked(id)
        }
        sendEvent(event)
    }

    override fun onActorClick(id: Int) {
        sendEvent(SeeMoreEvent.ActorClicked(id))
    }

    override fun onNavigateBack() {
        sendEvent(SeeMoreEvent.NavigateBack)
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }
}
