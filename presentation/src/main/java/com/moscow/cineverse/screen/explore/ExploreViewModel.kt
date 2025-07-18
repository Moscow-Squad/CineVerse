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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
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
                searchResult = it.searchResult.plus(ExploreTabsPages.MOVIES.toTitle() to items.map { movie ->
                    movie.toUi(
                        uiState.value.moviesGenres
                    )
                }),
                isLoading = false
            )
        }
        checkEmptySearchResult(items)
        updateContentList()
    }

    private fun onSeriesSearchSuccess(items: List<Series>) {
        updateState {
            it.copy(
                searchResult = it.searchResult.plus(ExploreTabsPages.SERIES.toTitle() to items.map { series ->
                    series.toUi(
                        uiState.value.seriesGenres
                    )
                }),
                isLoading = false
            )
        }
        checkEmptySearchResult(items)
        updateContentList()
    }

    private fun onActorSearchSuccess(actors: List<Actor>) {
        updateState { state ->
            state.copy(
                searchResult = state.searchResult.plus(ExploreTabsPages.ACTORS.toTitle() to actors.map { it.toUi() }),
                isLoading = false
            )
        }
        checkEmptySearchResult(actors)
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
            flowAction = { getLocalSuggestions.localSuggestions() },
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
               localSuggestions = filteredLocalSuggestions ,
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
    }

    override fun onSearchQuery() {
        updateState {
            it.copy(
                showHistory = false,
                showSuggestions = false,
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
        updateState {
            it.copy(
                moviesGenres = listOf(
                    ExploreScreenState.GenreUi(
                        id = 0,
                        name = "All"
                    )
                ) + genres.map { genre -> genre.toUi() })
        }
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
        updateState {
            it.copy(
                seriesGenres = listOf(
                    ExploreScreenState.GenreUi(
                        id = 0,
                        name = "All"
                    )
                ) + genres.map { genre -> genre.toUi() })
        }
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
        checkEmptySearchResult(movies)
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
                series = series.map { series -> series.toUi(uiState.value.seriesGenres) }

            )
        }
        checkEmptySearchResult(series)
        updateContentList()
    }

    private fun onGetSeriesByGenreIdFailed(e: Throwable) {

    }

    override fun onMovieGenreSelected(genreId: Int) {
        if (uiState.value.selectedMovieGenre == genreId) return
        if (genreId == 0) loadMovies() else getMoviesByGenreId(genreId)
        updateState { it.copy(selectedMovieGenre = genreId) }
    }

    override fun onSeriesGenreSelected(genreId: Int) {
        if (uiState.value.selectedSeriesGenre == genreId) return
        if (genreId == 0) loadSeries() else getSeriesByGenreId(genreId)
        updateState { it.copy(selectedSeriesGenre = genreId) }
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

    private fun onLoadSeriesSuccess(series: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                series = series.map { series -> series.toUi(uiState.value.seriesGenres) },
            )
        }
        checkEmptySearchResult(series)
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
        checkEmptySearchResult(movie)
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

    override fun <T> checkEmptySearchResult(list: List<T>) {
        updateState {
            it.copy(
                isContentEmpty = list.isEmpty()
            )
        }
    }
}