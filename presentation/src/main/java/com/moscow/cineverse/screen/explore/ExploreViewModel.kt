package com.moscow.cineverse.screen.explore

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUiState
import com.moscow.cineverse.utlis.ViewMode
import com.moscow.domain.model.Genre
import com.moscow.domain.model.MediaType
import com.moscow.domain.repository.blur.BlurProvider
import com.moscow.domain.usecase.genre.GenreUseCase
import com.moscow.domain.usecase.movie.GetMovieByGenreIdUseCase
import com.moscow.domain.usecase.movie.GetPopularMoviesUseCase
import com.moscow.domain.usecase.search.CacheSearchQueryUseCase
import com.moscow.domain.usecase.search.ClearSearchHistoryUseCase
import com.moscow.domain.usecase.search.GetLocalSuggestionsUseCase
import com.moscow.domain.usecase.search.SearchUseCase
import com.moscow.domain.usecase.search.SuggestionUseCase
import com.moscow.domain.usecase.series.GetPopularSeriesUseCase
import com.moscow.domain.usecase.series.GetSeriesByGenreIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val suggestionUseCase: SuggestionUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
    private val getLocalSuggestionsUseCase: GetLocalSuggestionsUseCase,
    private val genreUseCase: GenreUseCase,
    private val getMovieByGenreIdUseCase: GetMovieByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
    private val cacheSearchQueryUseCase: CacheSearchQueryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    private val blurProvider: BlurProvider
) : BaseViewModel<ExploreScreenState, ExploreScreenEffects>(ExploreScreenState()),
    ExploreInteractionListener {

    lateinit var contentList: Flow<PagingData<Any>>

    private lateinit var _movies: Flow<PagingData<MediaItemUiState>>
    private lateinit var _series: Flow<PagingData<MediaItemUiState>>

    private lateinit var _moviesSearch: Flow<PagingData<MediaItemUiState>>
    private lateinit var _seriesSearch: Flow<PagingData<MediaItemUiState>>
    private lateinit var _actorSearch: Flow<PagingData<ActorUiState>>

    private var isMoviesEmpty = false
    private var isSeriesEmpty = false
    private var isActorsEmpty = false

    init {
        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        observeKeyword()
        observeBlur()
    }

    private fun observeBlur() {
        viewModelScope.launch {
            blurProvider.blurFlow.collect { enableBlur ->
                updateState { it.copy(enableBlur = enableBlur) }
            }
        }
    }

    override fun searchMovie() {
        var isFirstLoad = true

        _moviesSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    val result =
                        searchUseCase.searchMovie(uiState.value.searchKeyWord, page).first()

                    if (page == 1 && isFirstLoad) {
                        isFirstLoad = false
                        viewModelScope.launch {
                            val isEmpty = result.isEmpty()
                            isMoviesEmpty = isEmpty

                            if (uiState.value.selectedTab == ExploreTabsPages.MOVIES) {
                                updateState {
                                    it.copy(isContentEmpty = isEmpty)
                                }
                            }
                        }
                    }

                    result
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUi(uiState.value.moviesGenres) } }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }

        if (uiState.value.selectedTab == ExploreTabsPages.MOVIES) {
            updateContentList()
        }
    }

    override fun searchSeries() {
        var isFirstLoad = true

        _seriesSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    val result =
                        searchUseCase.searchSeries(uiState.value.searchKeyWord, page).first()

                    if (page == 1 && isFirstLoad) {
                        isFirstLoad = false
                        viewModelScope.launch {
                            val isEmpty = result.isEmpty()
                            isSeriesEmpty = isEmpty

                            if (uiState.value.selectedTab == ExploreTabsPages.SERIES) {
                                updateState {
                                    it.copy(isContentEmpty = isEmpty)
                                }
                            }
                        }
                    }

                    result
                }
            }
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.toUi(uiState.value.seriesGenres + uiState.value.moviesGenres)
                }
            }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }

        if (uiState.value.selectedTab == ExploreTabsPages.SERIES) {
            updateContentList()
        }
    }

    override fun searchActor() {
        var isFirstLoad = true

        _actorSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    val result =
                        searchUseCase.searchActor(uiState.value.searchKeyWord, page).first()

                    if (page == 1 && isFirstLoad) {
                        isFirstLoad = false
                        viewModelScope.launch {
                            val isEmpty = result.isEmpty()
                            isActorsEmpty = isEmpty

                            if (uiState.value.selectedTab == ExploreTabsPages.ACTORS) {
                                updateState {
                                    it.copy(isContentEmpty = isEmpty)
                                }
                            }
                        }
                    }

                    result
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUi() } }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }

        if (uiState.value.selectedTab == ExploreTabsPages.ACTORS) {
            updateContentList()
        }
    }

    private fun resetSearchResults() {
        if (::_moviesSearch.isInitialized) {
            _moviesSearch = Pager(
                config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                pagingSourceFactory = { BasePagingSource { page -> emptyList<MediaItemUiState>() } }
            ).flow.cachedIn(viewModelScope)
        }

        if (::_seriesSearch.isInitialized) {
            _seriesSearch = Pager(
                config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                pagingSourceFactory = { BasePagingSource { page -> emptyList<MediaItemUiState>() } }
            ).flow.cachedIn(viewModelScope)
        }

        if (::_actorSearch.isInitialized) {
            _actorSearch = Pager(
                config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                pagingSourceFactory = { BasePagingSource { page -> emptyList<ActorUiState>() } }
            ).flow.cachedIn(viewModelScope)
        }
    }

    override fun getMoviesByGenreId(genreId: Int) {
        _movies = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getMovieByGenreIdUseCase(genreId, page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { movie -> movie.toUi(uiState.value.moviesGenres) }
        }
            .cachedIn(viewModelScope)
        updateContentList()
    }

    override fun getSeriesByGenreId(genreId: Int) {
        _series = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getSeriesByGenreIdUseCase(genreId, page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { series -> series.toUi(uiState.value.seriesGenres) }
        }
            .cachedIn(viewModelScope)
        updateContentList()
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }

    @OptIn(FlowPreview::class)
    private fun observeKeyword() {
        uiState
            .map { it.searchKeyWord }
            .debounce(300)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { keyword ->
                getSuggestions(keyword)
                updateState { it.copy(showHistory = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun getSuggestions(keyword: String, page: Int = 1) {
        launchWithResult(
            action = { suggestionUseCase.getSuggestions(keyword, page) },
            onSuccess = ::onSuccessLoadingSuggestions,
            onError = { updateState { it.copy(remoteSuggestions = listOf(uiState.value.searchKeyWord))} },
        )
    }

    private fun onSuccessLoadingSuggestions(suggestion: List<String>) {
        viewModelScope.launch {
            if (suggestion.isEmpty()) {
                updateState { it.copy(remoteSuggestions = listOf(uiState.value.searchKeyWord)) }
            } else {
                updateState { it.copy(remoteSuggestions = suggestion) }
            }
        }
    }

    override fun onSearchBarClickedOn() {
        updateState { it.copy(isSearchBarClickedOn = true, showSuggestions = true) }
        getHistoryData()
    }

    private fun getHistoryData() {
        launchWithFlow(
            flowAction = { getLocalSuggestionsUseCase() },
            onSuccess = ::onGetHistoryDataSuccess,
            onError = ::onGetHistoryDataFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetHistoryDataSuccess(suggestions: List<String>) {
        val suggestions = suggestions.map { SuggestItemUiState(it, isHistory = true) }.reversed()
        updateState { it.copy(localSuggestions = suggestions, showHistory = true) }
    }

    private fun onGetHistoryDataFailed(e: Int) {
        updateState { it.copy(error = e) }
    }

    override fun onCancelButtonClicked() {
        if (uiState.value.selectedTab == ExploreTabsPages.ACTORS) {
            updateState { it.copy(selectedTab = ExploreTabsPages.MOVIES) }
        }

        isMoviesEmpty = false
        isSeriesEmpty = false
        isActorsEmpty = false

        updateState {
            it.copy(
                isSearch = false,
                searchKeyWord = "",
                showHistory = false,
                showSuggestions = false,
                isLoading = false,
                remoteSuggestions = emptyList(),
                isContentEmpty = false
            )
        }
        updateContentList()
    }

    override fun onSearchValueChange(text: String) {
        if (text.length <= 50) {
            updateState {
                it.copy(
                    searchKeyWord = text,
                    showSuggestions = true,
                    showHistory = text.isBlank(),
                    remoteSuggestions = emptyList(),
                    isContentEmpty = false
                )
            }
        }
    }

    override fun onSearchWordDetected(searchKeyWord: List<String>) {
        updateState {
            it.copy(
                searchKeyWord = searchKeyWord[0],
                showSuggestions = true,
            )
        }
    }

    override fun onClickSuggestion(suggestion: SuggestItemUiState) {
        updateState {
            it.copy(
                searchKeyWord = suggestion.title,
                isContentEmpty = false,
                isSearch = true
            )
        }
        resetSearchResults()
        launchAndForget(
            action = {
                suggestion.isHistory.let {
                    if (!it) {
                        cacheSearchQueryUseCase.cacheSearchQuery(suggestion.title)
                    }
                    searchMovie()
                    searchSeries()
                    searchActor()
                }
            },
            onSuccess = {
                updateState {
                    it.copy(showHistory = false, showSuggestions = false)
                }
                updateContentList()
            },
            onError = ::onSearchQueryError
        )
    }

    override fun onSearchQuery() {
        updateState {
            it.copy(
                isSearch = true,
                showHistory = false,
                showSuggestions = false,
                isLoading = true,
                isContentEmpty = false
            )
        }
        resetSearchResults()
        launchAndForget(
            action = {
                uiState.value.localSuggestions.any { it.title == uiState.value.searchKeyWord }
                    .let { isQueryInHistory ->
                        searchMovie()
                        searchSeries()
                        searchActor()
                        if (!isQueryInHistory)
                            cacheSearchQueryUseCase.cacheSearchQuery(uiState.value.searchKeyWord)
                    }
            },
            onSuccess = {
                updateState { it.copy(isLoading = false, showHistory = false) }
                updateContentList()
            },
            onError = ::onSearchQueryError
        )
    }

    private fun onSearchQueryError(e: Int) {
        updateState {
            it.copy(
                shouldShowError = true,
                isLoading = false,
                error = e,
                isSearch = false
            )
        }
    }

    override fun clearAllLocalSuggestions() {
        launchAndForget(
            action = clearSearchHistoryUseCase::clearSearchHistory,
            onError = ::onClearSearchHistoryError
        )
    }

    private fun onClearSearchHistoryError(e: Int) {
        updateState { it.copy(showHistory = false, error = e) }
    }

    override fun getMoviesGenres() {
        launchWithResult(
            action = { genreUseCase.getMoviesGenres() },
            onSuccess = ::onMoviesGenresSuccess,
            onError = ::onMoviesGenresFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                moviesGenres = listOf(
                    ExploreScreenState.GenreUiState(
                        id = 0,
                        name = "All"
                    )
                ) + genres.map { genre -> genre.toUi() })
        }
        updateState { it.copy(genres = it.moviesGenres) }
    }

    private fun onMoviesGenresFailed(e: Int) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e) }
    }

    override fun getSeriesGenres() {
        launchWithResult(
            action = { genreUseCase.getSeriesGenres() },
            onSuccess = ::onSeriesGenresSuccess,
            onError = ::onSeriesGenresFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun onKeyboardClick() {
        getSuggestions(uiState.value.searchKeyWord)
    }

    private fun onSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                seriesGenres = listOf(
                    ExploreScreenState.GenreUiState(
                        id = 0,
                        name = "All"
                    )
                ) + genres.map { genre -> genre.toUi() })
        }
    }

    private fun onSeriesGenresFailed(e: Int) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e) }
    }

    override fun onMovieGenreSelected(genreId: Int) {
        if (uiState.value.selectedMovieGenre == genreId) return
        viewModelScope.launch {
            if (genreId == 0) getMovies() else getMoviesByGenreId(genreId)
        }
        updateState { it.copy(selectedMovieGenre = genreId) }
    }

    override fun onSeriesGenreSelected(genreId: Int) {
        if (uiState.value.selectedSeriesGenre == genreId) return
        viewModelScope.launch {
            if (genreId == 0) getSeries() else getSeriesByGenreId(genreId)
        }
        updateState { it.copy(selectedSeriesGenre = genreId) }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }

    override fun onMediaItemClicked(mediaItemUiState: MediaItemUiState) {
        if (mediaItemUiState.mediaType == MediaType.Movie)
            sendEvent(ExploreScreenEffects.MovieClicked(mediaItemUiState.id))
        else
            sendEvent(ExploreScreenEffects.SeriesClicked(mediaItemUiState.id))
    }

    override fun onActorClick(actorId: Int) {
        sendEvent(ExploreScreenEffects.ActorClicked(actorId))
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        if (uiState.value.selectedTab == tab) return

        val isEmpty = if (uiState.value.searchKeyWord.isNotBlank()) {
            when (tab) {
                ExploreTabsPages.MOVIES -> isMoviesEmpty
                ExploreTabsPages.SERIES -> isSeriesEmpty
                ExploreTabsPages.ACTORS -> isActorsEmpty
            }
        } else false

        updateState {
            it.copy(
                selectedTab = tab,
                isContentEmpty = isEmpty
            )
        }
        updateContentList()

        viewModelScope.launch {
            delay(500)
            checkEmptyStateForCurrentTab()
        }
    }

    override fun onRefresh() {
        updateState {
            it.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = "",
                isContentEmpty = false
            )
        }

        isMoviesEmpty = false
        isSeriesEmpty = false
        isActorsEmpty = false

        if (uiState.value.searchKeyWord != "") {
            onSearchQuery()
        } else {
            getMoviesGenres()
            getSeriesGenres()
            getSeries()
            getMovies()
        }
    }

    private fun getMovies() {
        _movies = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getPopularMoviesUseCase(page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { movie -> movie.toUi(uiState.value.moviesGenres) }
        }
            .cachedIn(viewModelScope)
        updateContentList()
    }

    private fun getSeries() {
        _series = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    getPopularSeriesUseCase(page)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { series -> series.toUi(uiState.value.seriesGenres) }
        }
            .cachedIn(viewModelScope)
        updateContentList()
    }

    private fun updateContentList() {
        if (uiState.value.searchKeyWord.isNotBlank()) {
            updateState { it.copy(shouldShowGenres = false) }
            contentList = when (uiState.value.selectedTab) {
                ExploreTabsPages.MOVIES -> _moviesSearch.map { pagingData ->
                    pagingData.map { item -> item as Any }
                }
                ExploreTabsPages.SERIES -> _seriesSearch.map { pagingData ->
                    pagingData.map { item -> item as Any }
                }
                ExploreTabsPages.ACTORS -> _actorSearch.map { pagingData ->
                    pagingData.map { item -> item as Any }
                }
            }
        } else {
            contentList = when (uiState.value.selectedTab) {
                ExploreTabsPages.MOVIES -> _movies.map { pagingData ->
                    pagingData.map { item -> item as Any }
                }
                ExploreTabsPages.SERIES -> _series.map { pagingData ->
                    pagingData.map { item -> item as Any }
                }
                ExploreTabsPages.ACTORS -> throw IllegalStateException("Actors tab should not be available without search")
            }

            updateState {
                it.copy(
                    genres = if (uiState.value.selectedTab == ExploreTabsPages.MOVIES)
                        it.moviesGenres else it.seriesGenres,
                    shouldShowGenres = true,
                    isContentEmpty = false
                )
            }
        }
    }
    private fun checkEmptyStateForCurrentTab() {
        if (uiState.value.searchKeyWord.isBlank()) return

        val isEmpty = when (uiState.value.selectedTab) {
            ExploreTabsPages.MOVIES -> isMoviesEmpty
            ExploreTabsPages.SERIES -> isSeriesEmpty
            ExploreTabsPages.ACTORS -> isActorsEmpty
        }

        updateState { it.copy(isContentEmpty = isEmpty) }
    }

    override fun <T> checkEmptySearchResult(list: List<T>) {
        updateState {
            it.copy(
                isContentEmpty = list.isEmpty()
            )
        }
    }
}