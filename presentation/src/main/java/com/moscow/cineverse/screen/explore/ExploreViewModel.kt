package com.moscow.cineverse.screen.explore

import androidx.lifecycle.viewModelScope
import com.android.domain.model.Actor
import com.android.domain.model.Genre
import com.android.domain.model.Movie
import com.android.domain.model.Series
import com.android.domain.usecase.CacheSearchQueryUseCase
import com.android.domain.usecase.ClearSearchHistoryUseCase
import com.android.domain.usecase.GenreUseCase
import com.android.domain.usecase.GetLocalSuggestions
import com.android.domain.usecase.GetMovieByGenreIdUseCase
import com.android.domain.usecase.GetMoviesUseCase
import com.android.domain.usecase.GetSeriesByGenreIdUseCase
import com.android.domain.usecase.GetSeriesUseCase
import com.android.domain.usecase.SearchUseCase
import com.android.domain.usecase.SuggestionUseCase
import com.moscow.cineverse.base.BaseViewModel
import com.moscow.cineverse.designSystem.component.ViewMode
import com.moscow.cineverse.designSystem.component.tabs.ExploreTabsPages
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val searchUseCase: SearchUseCase,
    private val suggestionUseCase: SuggestionUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    val getLocalSuggestions: GetLocalSuggestions,
    val genreUseCase: GenreUseCase,
    private val getMovieByGenreIdUseCase: GetMovieByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
    private val cacheSearchQueryUseCase: CacheSearchQueryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
) : BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {

    init {
        getMoviesGenres()
        getSeriesGenres()
        loadMovies()
        loadSeries()
        observeKeyword()
    }

    override fun getSavedHistoryItems(suggestion: String) {
        TODO("Not yet implemented")
    }

    override fun searchMovie(isHistory: Boolean) {
        launchWithFlow(
            flowAction = {
                searchUseCase.searchMovie(
                    uiState.value.searchKeyWord,
                    isHistory = isHistory
                )
            },
            onSuccess = ::onMovieSearchSuccess,
            onError = ::onMovieSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally,
        )
    }

    override fun searchSeries(isHistory: Boolean) {
        launchWithFlow(
            flowAction = { searchUseCase.searchSeries(uiState.value.searchKeyWord, isHistory) },
            onSuccess = ::onSeriesSearchSuccess,
            onError = ::onSeriesSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    override fun searchActor(isHistory: Boolean) {
        launchWithFlow(
            flowAction = { searchUseCase.searchActor(uiState.value.searchKeyWord, isHistory) },
            onSuccess = ::onActorSearchSuccess,
            onError = ::onActorsSearchFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onMovieSearchSuccess(items: List<Movie>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(ExploreTabsPages.MOVIES.toTitle() to items.map {
                    it.toUi(
                        uiState.value.moviesGenres
                    )
                }),
                isLoading = false
            )
        }
        updateContentList()
    }

    private fun onSeriesSearchSuccess(items: List<Series>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(ExploreTabsPages.SERIES.toTitle() to items.map {
                    it.toUi(
                        uiState.value.seriesGenres
                    )
                }),
                isLoading = false
            )
        }
        updateContentList()
    }

    private fun onActorSearchSuccess(actors: List<Actor>) {
        updateState { state ->
            state.copy(
                searchResult = state.searchResult.plus(ExploreTabsPages.ACTORS.toTitle() to actors.map { it.toUi() }),
                isLoading = false
            )
        }
        updateContentList()
    }

    private fun onMovieSearchFailed(e: Throwable) {
        updateState { it.copy(isLoading = false, error = e.message) }
    }

    private fun onSeriesSearchFailed(e: Throwable) {
        updateState { it.copy(isLoading = false, error = e.message) }
    }

    private fun onActorsSearchFailed(e: Throwable) {
        updateState { it.copy(isLoading = false, error = e.message) }
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }


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
    }

    override fun onSearchBarClickedOn() {
        updateState { it.copy(isSearchBarClickedOn = true, showSuggestions = true) }
        getHistoryData()
    }

    private fun getHistoryData() {
        launchWithFlow(
            flowAction = { getLocalSuggestions.localSuggestions() },
            onSuccess = ::onGetHistoryDataSuccess,
            onError = ::onGetHistoryDataFailed,
            onStart = ::onLoading,
            onFinally = {}
        )
    }

    private fun onGetHistoryDataSuccess(suggestions: List<String>) {
        val suggestions = suggestions.map { SuggestItemUiState(it, isHistory = true) }
        updateState { it.copy(localSuggestions = suggestions, showHistory = true) }
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
            state.copy(
                displayedSuggestions = uiState.value.localSuggestions.filter {
                    it.title.contains(
                        uiState.value.searchKeyWord,
                        ignoreCase = true
                    )
                } + uiState.value.remoteSuggestions
//            .filter { remoteSuggestions -> remoteSuggestions !in uiState.value.localSuggestions.map { it.title } }
                    .map { SuggestItemUiState(it, isHistory = false) }

            )
        }

    }

    override fun onClickSuggestion(suggestion: SuggestItemUiState) {
        updateState {
            it.copy(
                searchKeyWord = suggestion.title,
            )
        }
        viewModelScope.launch {
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
                remoteSuggestions = emptyList()
            )
        }
    }

    override fun onSearchQuery() {
        launchAndForget(
            action = {
                updateState {
                    it.copy(
                        showHistory = false,
                        showSuggestions = false,
                        remoteSuggestions = emptyList()
                    )
                }

                uiState.value.localSuggestions.any { it.title == uiState.value.searchKeyWord }
                    .let { isQueryInHistory ->
                        if (!isQueryInHistory)
                            cacheSearchQueryUseCase.cacheSearchQuery(uiState.value.searchKeyWord)
                        searchMovie(isQueryInHistory)
                        searchSeries(isQueryInHistory)
                        searchActor(isQueryInHistory)
                    }
            },
            onError = ::onSearchQueryError
        )
    }

    private fun onSearchQueryError(e: Throwable) {
        updateState { it.copy(error = e.message) }
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
        launchWithFlow(
            flowAction = { genreUseCase.getMoviesGenres() },
            onSuccess = ::onMoviesGenresSuccess,
            onError = ::onMoviesGenresFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(moviesGenres = genres.map { it.toUi() }) }
        updateState { it.copy(genres = it.moviesGenres) }
    }

    private fun onMoviesGenresFailed(e: Throwable) {

    }


    override fun getSeriesGenres() {
        launchWithFlow(
            flowAction = { genreUseCase.getSeriesGenres() },
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
        updateState { it.copy(seriesGenres = genres.map { it.toUi() }) }
    }

    private fun onSeriesGenresFailed(e: Throwable) {

    }

    override fun getMoviesByGenreId(genreId: Int) {
        launchWithFlow(
            flowAction = { getMovieByGenreIdUseCase.getMovieByGenreId(genreId) },
            onSuccess = ::onGetMovieByGenreIdSuccess,
            onError = ::onGetMovieByGenreIdFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetMovieByGenreIdSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                movies = movies.map { movie -> movie.toUi(uiState.value.moviesGenres) }
            )
        }
        updateContentList()
    }

    private fun onGetMovieByGenreIdFailed(e: Throwable) {

    }

    override fun getSeriesByGenreId(genreId: Int) {
        launchWithFlow(
            flowAction = { getSeriesByGenreIdUseCase.getSeriesByGenreId(genreId) },
            onSuccess = ::onGetSeriesByGenreIdSuccess,
            onError = ::onGetSeriesByGenreIdFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetSeriesByGenreIdSuccess(series: List<Series>) {
        updateState {
            it.copy(
                series = series.map { serie -> serie.toUi(uiState.value.seriesGenres) }

            )
        }
        updateContentList()
    }

    private fun onGetSeriesByGenreIdFailed(e: Throwable) {

    }


    override fun onGenreSelected(genreId: Int) {

        if (uiState.value.selectedGenre == genreId) return

        updateState { it.copy(selectedGenre = genreId) }
        when (uiState.value.selectedTab) {
            ExploreTabsPages.MOVIES -> {
                if (genreId == 0)
                    loadMovies()
                else
                    getMoviesByGenreId(genreId)
            }

            ExploreTabsPages.SERIES ->
                if (genreId == 0)
                    loadSeries()
                else
                    getSeriesByGenreId(genreId)

            ExploreTabsPages.ACTORS -> {}
        }
    }

    override fun onViewModeChanged(viewMode: ViewMode) {
        updateState { it.copy(viewMode = viewMode) }
    }

    override fun onMovieClick(movieId: Int) {
        sendEvent(ExploreScreenEvents.MovieClicked(movieId))
    }

    override fun onActorClick(actorId: Int) {
        sendEvent(ExploreScreenEvents.ActorClicked(actorId))
    }

    override fun onTabSelected(tab: ExploreTabsPages) {
        if (uiState.value.selectedTab == tab) return
        updateState { it.copy(selectedTab = tab) }
        updateContentList()
    }

    override fun onRefresh() {
    }

    private fun loadSeries() {
        launchWithFlow(
            flowAction = { getSeriesUseCase() },
            onSuccess = ::onLoadSeriesSuccess,
            onError = ::onLoadSeriesFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onLoadSeriesSuccess(movie: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                series = movie.map { movie -> movie.toUi(uiState.value.seriesGenres) },
            )
        }
        updateContentList()
    }

    private fun onLoadSeriesFailed(e: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }
    }

    private fun loadMovies() {
        launchWithFlow(
            flowAction = { getMoviesUseCase() },
            onSuccess = ::onLoadMoviesSuccess,
            onError = ::onLoadMoviesFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onLoadMoviesSuccess(movie: List<Movie>) {
        updateState {
            it.copy(
                isLoading = false,
                movies = movie.map { movie -> movie.toUi(uiState.value.moviesGenres) },
            )
        }
        updateContentList()
    }

    private fun onLoadMoviesFailed(e: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }
    }

    private fun updateContentList() {
        if (uiState.value.searchResult.isNotEmpty() &&
            uiState.value.searchResult.containsKey(uiState.value.selectedTab.toTitle())
        )
            updateState {
                it.copy(
                    contentList = it.searchResult.getOrDefault(
                        it.selectedTab.toTitle(),
                        emptyList()
                    ),
                    shouldShowGenres = false
                )
            }
        else {
            updateState {
                it.copy(
                    genres = if (it.selectedTab == ExploreTabsPages.MOVIES)
                        it.moviesGenres
                    else it.seriesGenres
                )
            }
            updateState {
                it.copy(
                    shouldShowGenres = it.selectedTab != ExploreTabsPages.ACTORS,
                    contentList = if (it.selectedTab == ExploreTabsPages.MOVIES)
                        it.movies
                    else
                        it.series

                )
            }
        }
    }
}