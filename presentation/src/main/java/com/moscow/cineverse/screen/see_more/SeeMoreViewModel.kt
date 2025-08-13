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
import com.moscow.cineverse.navigation.routes.SeeMoreRoute
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.cineverse.screen.home.HomeFeaturedCollections
import com.moscow.cineverse.screen.home.HomeFeaturedItems
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.domain.model.Movie
import com.moscow.domain.model.UserType
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.collection.GetCollectionDetailsUseCase
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.movie.GetMatchesYourVibesMoviesUseCase
import com.moscow.domain.usecase.movie.GetRecentlyReleasedMoviesUseCase
import com.moscow.domain.usecase.series.GetTopRatedTVShowsUseCase
import com.moscow.domain.usecase.movie.GetUpcomingMoviesUseCase
import com.moscow.domain.usecase.preference.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val getMatchesYourVibesMoviesUseCase: GetMatchesYourVibesMoviesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getCollectionDetailsUseCase: GetCollectionDetailsUseCase,
    private val genreUseCase: GenreUseCase,
    private val blurProvider: BlurProvider,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<SeeMoreUiState, SeeMoreEvent>(SeeMoreUiState()), SeeMoreInteractionListener {

    private val _pagingDataFlow = MutableStateFlow<Flow<PagingData<MediaItemUiState>>>(emptyFlow())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()
    private val category = savedStateHandle.get<String>(SeeMoreRoute.CATEGORY) ?: ""

    init {
        getMovieGenre()
        getSeriesGenre()
        loadContent()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun loadContent() {
        updateState { it.copy(isLoading = true) }
        val pageSize = 20

        viewModelScope.launch {
            try {
                val pagingFlow = when {
                    category.startsWith("FEATURED_COLLECTION_") -> {
                        val genreId = category.removePrefix("FEATURED_COLLECTION_").toInt()
                        updateState { state ->
                            state.copy(
                                title = HomeFeaturedCollections.entries.find { it.genreId == genreId }?.title ?: 0
                            )
                        }
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page ->
                                getMatchesYourVibesMoviesUseCase(genreId, page, forceRefresh = true)
                            }
                        )
                    }

                    category == HomeFeaturedItems.RECENTLY_RELEASED.name -> {
                        updateState { it.copy(title = HomeFeaturedItems.RECENTLY_RELEASED.titleResource) }
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page ->
                                getRecentlyReleasedMoviesUseCase(
                                    page = page,
                                    forceRefresh = true
                                )
                            },
                        )
                    }

                    category == HomeFeaturedItems.UPCOMING_MOVIES.name -> {
                        updateState { it.copy(title = HomeFeaturedItems.UPCOMING_MOVIES.titleResource) }
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page ->
                                getUpcomingMoviesUseCase(
                                    page = page,
                                    forceRefresh = true
                                )
                            },
                        )
                    }

                    category == HomeFeaturedItems.MATCHES_YOUR_VIBE.name -> {
                        updateState { it.copy(title = HomeFeaturedItems.MATCHES_YOUR_VIBE.titleResource) }
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page ->
                                getMatchesYourVibesMoviesUseCase(
                                    28,
                                    page = page,
                                    forceRefresh = true
                                )
                            },
                        )
                    }

                    category == HomeFeaturedItems.TOP_RATED_TV_SHOWS.name -> {
                        updateState { it.copy(title = HomeFeaturedItems.TOP_RATED_TV_SHOWS.titleResource) }
                        createPagingFlow(
                            pageSize = pageSize,
                            fetchData = { page -> getTopRatedTVShowsUseCase(page, forceRefresh = true) },
                        )
                    }

                    category == HomeFeaturedItems.YOU_RECENTLY_VIEWED.name -> {
                        updateState { it.copy(title = HomeFeaturedItems.YOU_RECENTLY_VIEWED.titleResource) }
                        val user = getUserDetailsUseCase()
                        when (user) {
                            is UserType.AuthenticatedUser -> {
                                createPagingFlow(
                                    pageSize = 20,
                                    fetchData = { page ->
                                        getCollectionDetailsUseCase(
                                            user.recentlyCollectionId,
                                            page
                                        )
                                    }
                                )
                            }

                            is UserType.GuestUser -> emptyFlow()
                        }
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
            },
            onError = { e ->
                updateState {
                    it.copy(
                        shouldShowError = true,
                        isLoading = false,
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }

    private fun getSeriesGenre() {
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
                        shouldShowError = true,
                        isLoading = false,
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }

    private fun <T : Any> createPagingFlow(
        pageSize: Int,
        fetchData: suspend (Int) -> List<T>
    ): Flow<PagingData<MediaItemUiState>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BasePagingSource(fetchData) }
        ).flow
            .map { pagingData ->
                pagingData.map { item ->
                    when (item) {
                        is Movie -> item.toUi(uiState.value.moviesGenres)
                        is Series -> item.toUi(uiState.value.seriesGenres)
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
