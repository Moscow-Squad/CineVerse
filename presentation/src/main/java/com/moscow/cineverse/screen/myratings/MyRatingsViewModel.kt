package com.moscow.cineverse.screen.myratings

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.ExploreTabsPages
import com.moscow.cineverse.screen.explore.toUi
import com.moscow.domain.model.MediaType
import com.moscow.domain.service.blur.BlurProvider
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.rating.CloseRatingTipUseCase
import com.moscow.domain.usecase.rating.GetRatedMoviesUseCase
import com.moscow.domain.usecase.rating.GetRatedSeriesUseCase
import com.moscow.domain.usecase.rating.GetShowRatingTipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRatingsViewModel @Inject constructor(
    private val ratedMoviesUseCase: GetRatedMoviesUseCase,
    private val ratedSeriesUseCase: GetRatedSeriesUseCase,
    private val genreUseCase: GenreUseCase,
    private val getShowRatingTipUseCase: GetShowRatingTipUseCase,
    private val closeRatingTipUseCase: CloseRatingTipUseCase,
    private val blurProvider: BlurProvider
) : BaseViewModel<MyRatingsUiState, MyRatingsEffect>(MyRatingsUiState()),
    MyRatingsInteractionListener {

    lateinit var contentList: Flow<PagingData<RatedMediaItem>>

    private lateinit var _ratedMovies: Flow<PagingData<RatedMediaItem>>
    private lateinit var _ratedSeries: Flow<PagingData<RatedMediaItem>>

    private var moviesPagingSource: BasePagingSource<RatedMediaItem>? = null
    private var seriesPagingSource: BasePagingSource<RatedMediaItem>? = null

    private var _isMoviesEmpty = false
    private var _isSeriesEmpty = false

    init {
        getRatedMovies()
        getRatedSeries()
        getMoviesGenres()
        getSeriesGenres()
        getShowTip()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    private fun getShowTip() {
        launchWithResult(
            action = getShowRatingTipUseCase::invoke,
            onSuccess = { res ->
                updateState {
                    it.copy(
                        showTip = res,
                        isLoading = false
                    )
                }
            },
            onError = { msg ->
                updateState {
                    it.copy(
                        shouldShowError = true,
                        isLoading = false,
                        errorMessage = msg
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } }
        )
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        if (uiState.value.selectedTab == tab) return

        updateState { it.copy(selectedTab = tab) }
        updateContentList()
        updateEmptyContentState()
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
                errorMessage = 0,
                isContentEmpty = false
            )
        }

        invalidateData()
    }

    override fun onEmptyStateButtonClicked() {
        sendEvent(MyRatingsEffect.NavigateToExplore)
    }

    override fun onTipCancelIconClicked() {
        updateState { it.copy(isLoading = false) }
        launchAndForget(
            action = { closeRatingTipUseCase() },
            onSuccess = { updateState { it.copy(showTip = false) } },
            onError = { msg ->
                updateState {
                    it.copy(
                        shouldShowError = true,
                        errorMessage = msg
                    )
                }
            }
        )
    }

    fun refreshDataIfNeeded() {
        invalidateData()
    }

    private fun invalidateData() {
        moviesPagingSource?.invalidate()
        seriesPagingSource?.invalidate()
        getRatedMovies()
        getRatedSeries()
    }

    private fun getRatedMovies() {
        _ratedMovies = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource<RatedMediaItem> { page ->
                    val result = ratedMoviesUseCase.invoke(page)
                    if (page == 1) {
                        _isMoviesEmpty = result.isEmpty()
                        updateEmptyContentState()
                    }
                    result.map { it.toUi(uiState.value.movieGenres) }
                }.also { moviesPagingSource = it }
            }
        ).flow.cachedIn(viewModelScope)

        updateContentList()
    }

    private fun getRatedSeries() {
        _ratedSeries = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource<RatedMediaItem> { page ->
                    val result = ratedSeriesUseCase.invoke(page)
                    if (page == 1) {
                        _isSeriesEmpty = result.isEmpty()
                        updateEmptyContentState()
                    }
                    result.map { it.toUi(uiState.value.seriesGenres) }
                }.also { seriesPagingSource = it }
            }
        ).flow.cachedIn(viewModelScope)

        updateContentList()
    }

    private fun updateContentList() {
        contentList = when (uiState.value.selectedTab) {
            ExploreTabsPages.MOVIES -> _ratedMovies
            ExploreTabsPages.SERIES -> _ratedSeries
            ExploreTabsPages.ACTORS -> throw IllegalStateException("Actors tab should not be available in ratings")
        }
    }

    private fun updateEmptyContentState() {
        val currentTab = uiState.value.selectedTab
        val isCurrentTabEmpty = when (currentTab) {
            ExploreTabsPages.MOVIES -> _isMoviesEmpty
            ExploreTabsPages.SERIES -> _isSeriesEmpty
            ExploreTabsPages.ACTORS -> false
        }

        updateState {
            it.copy(isContentEmpty = isCurrentTabEmpty)
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
                        errorMessage = e,
                        shouldShowError = true
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
                        errorMessage = e,
                        shouldShowError = true
                    )
                }
            },
            onStart = { updateState { it.copy(isLoading = true) } },
        )
    }
}