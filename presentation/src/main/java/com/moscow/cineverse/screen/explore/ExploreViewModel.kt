package com.moscow.cineverse.screen.explore

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.MediaType
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.usecase.search.CacheSearchQueryUseCase
import com.android.domain.usecase.search.ClearSearchHistoryUseCase
import com.android.domain.usecase.genre.GenreUseCase
import com.android.domain.usecase.search.GetLocalSuggestionsUseCase
import com.android.domain.usecase.movie.GetMovieByGenreIdUseCase
import com.android.domain.usecase.movie.GetPopularMoviesUseCase
import com.android.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.series.GetPopularSeriesUseCase
import com.android.domain.usecase.search.SearchUseCase
import com.android.domain.usecase.search.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import com.moscow.cineverse.common_ui_state.MediaItemUiState
import com.moscow.cineverse.paging.BasePagingSource
import com.moscow.cineverse.screen.explore.ExploreScreenState.ActorUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val searchUseCase: SearchUseCase,
    private val suggestionUseCase: SuggestionUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
    val getLocalSuggestionsUseCase: GetLocalSuggestionsUseCase,
    val genreUseCase: GenreUseCase,
    private val getMovieByGenreIdUseCase: GetMovieByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
    private val cacheSearchQueryUseCase: CacheSearchQueryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
) : BaseViewModel<ExploreScreenState, ExploreScreenEffects>(ExploreScreenState()),
    ExploreInteractionListener {

    lateinit var contentList: Flow<PagingData<Any>>
//    val contentList : StateFlow<PagingData<Any>> = uiState
//        .map { it.selectedTab }
//        .distinctUntilChanged()
//        .flatMapLatest { tab ->
//            if (uiState.value.searchKeyWord.isNotEmpty()){
//                when (tab) {
//                    ExploreTabsPages.MOVIES -> _moviesSearch as Flow<PagingData<Any>>
//                    ExploreTabsPages.SERIES -> _seriesSearch as Flow<PagingData<Any>>
//                    ExploreTabsPages.ACTORS -> _actorSearch as Flow<PagingData<Any>>
//                }
//            }else
//                when (tab) {
//                    ExploreTabsPages.MOVIES -> movies as Flow<PagingData<Any>>
//                    ExploreTabsPages.SERIES -> series as Flow<PagingData<Any>>
//                    ExploreTabsPages.ACTORS -> movies as Flow<PagingData<Any>>
//                }
//        }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private lateinit var movies : Flow<PagingData<MediaItemUiState>>
    private lateinit var series : Flow<PagingData<MediaItemUiState>>

    private lateinit var _moviesSearch : Flow<PagingData<MediaItemUiState>>
    private lateinit var _seriesSearch  : Flow<PagingData<MediaItemUiState>>
    private lateinit var _actorSearch  : Flow<PagingData<ActorUiState>>


    init {
        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        observeKeyword()
    }

    override fun searchMovie(isHistory: Boolean) {
        _moviesSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    searchUseCase.searchMovie(uiState.value.searchKeyWord, page, isHistory).first()
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUi(uiState.value.moviesGenres) } }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }
    }

    override fun searchSeries(isHistory: Boolean) {
        _seriesSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    searchUseCase.searchSeries(uiState.value.searchKeyWord, page, isHistory).first()
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUi(uiState.value.seriesGenres) } }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }
    }

    override fun searchActor(isHistory: Boolean) {
        _actorSearch = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BasePagingSource { page ->
                    searchUseCase.searchActor(uiState.value.searchKeyWord, page, isHistory).first()
                }
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toUi() } }
            .cachedIn(viewModelScope)

        updateState { it.copy(isLoading = false) }
    }

    override fun getMoviesByGenreId(genreId: Int) {
        movies = Pager(
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
        series = Pager(
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
            onError = { },
            onStart = { },
            onFinally = { }
        )
    }

    private fun onSuccessLoadingSuggestions(suggestion: List<String>) {
        viewModelScope.launch {
            updateState { it.copy(remoteSuggestions = suggestion) }
        }
        updateDisplayedSuggestions()
    }

    override fun onSearchBarClickedOn() {
        updateState { it.copy(isSearchBarClickedOn = true, showSuggestions = true) }
        getHistoryData()
    }

    private fun getHistoryData() {
        launchWithFlow(
            flowAction = { getLocalSuggestionsUseCase.invoke() },
            onSuccess = ::onGetHistoryDataSuccess,
            onError = ::onGetHistoryDataFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetHistoryDataSuccess(suggestions: List<String>) {
        val suggestions = suggestions.map { SuggestItemUiState(it, isHistory = true) }
        updateState { it.copy(localSuggestions = suggestions, showHistory = true) }
        updateDisplayedSuggestions()
    }

    private fun onGetHistoryDataFailed(e: Throwable) {
    }

    override fun onCancelButtonClicked() {
        if (uiState.value.selectedTab == ExploreTabsPages.ACTORS) {
            updateState {
                it.copy(
                    selectedTab = ExploreTabsPages.MOVIES
                )
            }
        }

        updateState {
            it.copy(
                searchKeyWord = "",
                showHistory = false,
                showSuggestions = false,
                isLoading = false,
                remoteSuggestions = emptyList(),
                searchResult = mutableMapOf()
            )
        }
        updateContentList()
    }

    override fun onSearchValueChange(text: String) {
        updateState {
            it.copy(
                searchKeyWord = text,
                showSuggestions = true,
                showHistory = text.isBlank(),
                remoteSuggestions = emptyList()
            )
        }
        updateDisplayedSuggestions()
    }

    override fun onSearchWordDetected(searchKeyWord: List<String>) {
        updateState {
            it.copy(
                searchKeyWord = searchKeyWord[0],
                showSuggestions = true,
            )
        }
    }

    private fun updateDisplayedSuggestions() {
        updateState { state ->
            val filteredLocalSuggestions = if (state.searchKeyWord.isBlank()) state.localSuggestions
            else state.localSuggestions.filter {
                it.title.contains(
                    state.searchKeyWord,
                    ignoreCase = true
                )
            }

            val mappedRemoteSuggestions = state.remoteSuggestions
                .map { SuggestItemUiState(it, isHistory = false) }


            state.copy(
                localSuggestions = filteredLocalSuggestions,
                remoteSuggestions = mappedRemoteSuggestions.map { it.title }
            )
        }
    }

    override fun onClickSuggestion(suggestion: SuggestItemUiState) {
        updateState {
            it.copy(
                searchKeyWord = suggestion.title,
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            suggestion.isHistory.let {
                if (!it) {
                    cacheSearchQueryUseCase.cacheSearchQuery(suggestion.title)
                }
                searchMovie(it)
                searchSeries(it)
                searchActor(it)
            }
        }
        updateState {
            it.copy(
                showHistory = false,
                showSuggestions = false,
            )
        }
        updateContentList()
    }

    override fun onSearchQuery() {
        updateState {
            it.copy(
                showHistory = false,
                showSuggestions = false,
                isLoading = true
            )
        }
        launchAndForget(
            action = {
                uiState.value.localSuggestions.any { it.title == uiState.value.searchKeyWord }
                    .let { isQueryInHistory ->
                        if (!isQueryInHistory)
                            cacheSearchQueryUseCase.cacheSearchQuery(uiState.value.searchKeyWord)
                        searchMovie(isQueryInHistory)
                        searchSeries(isQueryInHistory)
                        searchActor(isQueryInHistory)
                    }
            },
            onSuccess = {
                updateState { it.copy(isLoading = false, showHistory = false) }
                updateContentList()
                        },
            onError = ::onSearchQueryError
        )
    }

    private fun onSearchQueryError(e: Throwable) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e.message) }
    }

    override fun clearAllLocalSuggestions() {
        launchAndForget(
            action = clearSearchHistoryUseCase::clearSearchHistory,
            onError = ::onClearSearchHistoryError
        )
    }

    private fun onClearSearchHistoryError(e: Throwable) {
        updateState { it.copy(showHistory = false, error = e.message) }
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

    private fun onMoviesGenresFailed(e: Throwable) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e.message) }
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

    private fun onSeriesGenresFailed(e: Throwable) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e.message) }
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
        updateState { it.copy(selectedTab = tab) }
        updateContentList()
    }

    override fun onRefresh() {
        updateState {
            it.copy(
                isLoading = true,
                shouldShowError = false,
                errorMessage = ""
            )
        }
        if (uiState.value.searchKeyWord != "") {
            onSearchQuery()
        } else {
            getMoviesGenres()
            getSeriesGenres()
            //loadMovies()
            //loadSeries()
        }
    }

    private fun getMovies(){
        movies = Pager(
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

    private fun getSeries(){
        series = Pager(
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

    private fun onLoadMoviesSuccess(movie: List<Movie>) {
//        updateState {
//            it.copy(
//                isLoading = false,
//                movies = movie.map { movie -> movie.toUi(uiState.value.moviesGenres) },
//            )
//        }
//        checkEmptySearchResult(movie)
//        updateContentList()
    }

    private fun onLoadMoviesFailed(e: Throwable) {
        updateState { it.copy(shouldShowError = true, isLoading = false, error = e.message) }
    }

    private fun updateContentList() {
//        updateState {
//            it.copy(
//                shouldShowGenres = uiState.value.searchKeyWord.isEmpty() && uiState.value.selectedTab != ExploreTabsPages.ACTORS
//            )
//        }
        if (uiState.value.searchKeyWord.isNotBlank()){
            updateState { it.copy(shouldShowGenres = false) }
            contentList = when (uiState.value.selectedTab) {
                ExploreTabsPages.MOVIES -> _moviesSearch
                ExploreTabsPages.SERIES -> _seriesSearch
                ExploreTabsPages.ACTORS -> _actorSearch
            } as Flow<PagingData<Any>>
        }else{
            contentList = when (uiState.value.selectedTab) {
                ExploreTabsPages.MOVIES -> movies
                ExploreTabsPages.SERIES -> series
                ExploreTabsPages.ACTORS -> null
            } as Flow<PagingData<Any>>

            updateState {
                it.copy(
                    genres = if (uiState.value.selectedTab == ExploreTabsPages.MOVIES)
                        it.moviesGenres else it.seriesGenres,
                    shouldShowGenres = true
                )
            }
        }

//        if (uiState.value.searchResult.isNotEmpty() &&
//            uiState.value.searchResult.containsKey(uiState.value.selectedTab.toTitle())
//        )
//            updateState {
//                it.copy(
//                    contentList = it.searchResult.getOrDefault(
//                        it.selectedTab.toTitle(),
//                        emptyList()
//                    ),
//                    shouldShowGenres = false
//                )
//            }
//        else {
//            updateState {
//                it.copy(
//                    genres = if (it.selectedTab == ExploreTabsPages.MOVIES)
//                        it.moviesGenres
//                    else it.seriesGenres,
//                    shouldShowGenres = it.selectedTab != ExploreTabsPages.ACTORS,
//                )
//            }
//
//            if (uiState.value.selectedTab == ExploreTabsPages.MOVIES)
//                contentList = movies as Flow<PagingData<Any>>
//            else
//                contentList = series as Flow<PagingData<Any>>

//            updateState {
//                it.copy(
//                    contentList = if (it.selectedTab == ExploreTabsPages.MOVIES)
//                        it.movies
//                    else
//                        it.series
//
//                )
//            }
        }

    override fun <T> checkEmptySearchResult(list: List<T>) {
        updateState {
            it.copy(
                isContentEmpty = list.isEmpty()
            )
        }
    }
}
